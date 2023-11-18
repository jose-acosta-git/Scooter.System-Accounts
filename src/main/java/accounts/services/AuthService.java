package accounts.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import accounts.config.JwtAuthenticationFilter;
import accounts.dtos.AuthResponseDto;
import accounts.dtos.LoginDto;
import accounts.dtos.RegisterDto;
import accounts.model.User;
import accounts.repositories.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    public ResponseEntity<AuthResponseDto> login(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = usersRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    public ResponseEntity<AuthResponseDto> register(RegisterDto request) {
        String requestRole = request.getRole();
        if (!requestRole.equals("ADMIN") && !requestRole.equals("USER") && !requestRole.equals("MAINTENANCE"))
            return ResponseEntity.badRequest().build();
        Optional<User> optionalUser = usersRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }


        User user = new User(
            request.getName(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            request.getPhone(),
            requestRole
        );
        usersRepository.save(user);
        return ResponseEntity.ok(new AuthResponseDto(jwtService.getToken(user)));   
    }

    public Boolean isTokenValid(HttpServletRequest request) {
        String token = authenticationFilter.getTokenFromRequest(request);
        if (token == null)
            return false;
        try {
            String email = jwtService.getUsernameFromToken(token);
            Optional<User> optionalUser = usersRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                return true;
            }
            return false;
        } catch(Exception e) {
            return false;
        }
    }
}