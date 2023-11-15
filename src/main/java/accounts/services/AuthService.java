package accounts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import accounts.dtos.AuthResponseDto;
import accounts.dtos.LoginDto;
import accounts.dtos.RegisterDto;
import accounts.model.Role;
import accounts.model.User;
import accounts.repositories.UsersRepository;

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

    public ResponseEntity<AuthResponseDto> login(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = usersRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    public ResponseEntity<AuthResponseDto> register(RegisterDto request) {
    String requestRole = request.getRole();
    Role role;
    if (requestRole.equals("admin")) {
        role = Role.ADMIN;
    } else if (requestRole.equals("user")) {
        role = Role.USER;
    } else if (requestRole.equals("maintenance")) {
        role = Role.MAINTENANCE;
    } else {
        return ResponseEntity.badRequest().build();
    }

    User user = new User(
        request.getName(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword()),
        request.getPhone(),
        role
    );
    usersRepository.save(user);
    return ResponseEntity.ok(new AuthResponseDto(jwtService.getToken(user)));
}
    
}
