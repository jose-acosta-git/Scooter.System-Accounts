package accounts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import accounts.config.JwtAuthenticationFilter;
import accounts.dtos.AccountDto;
import accounts.model.Account;
import accounts.model.User;
import accounts.repositories.AccountsRepository;
import accounts.repositories.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;

    public ResponseEntity<User> linkNewAccount(Integer userId, AccountDto dto) {
        Optional<User> optionalUser = usersRepository.findById(userId);
		if (!optionalUser.isPresent())
			return ResponseEntity.notFound().build();

		User user = optionalUser.get();
		Account account = new Account(dto.getRegistrationDate(), dto.getBalance(), dto.getMercadoPagoId());
		accountsRepository.save(account);
		user.addAccount(account);
		return ResponseEntity.ok(usersRepository.save(user));
    }

	public ResponseEntity<User> linkAccount(Integer userId, Integer accountId) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		Optional<Account> optionalAccount = accountsRepository.findById(accountId);
		if (!optionalUser.isPresent() || !optionalAccount.isPresent())
			return ResponseEntity.notFound().build();
		
		User user = optionalUser.get();
		Account account = optionalAccount.get();
		if (user.getAccounts().contains(account))
			return ResponseEntity.badRequest().build();
		user.addAccount(account);
		return ResponseEntity.ok(usersRepository.save(user));
	}

    public ResponseEntity<List<User>> findAll(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        String email = jwtService.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		var role = userDetails.getAuthorities().iterator().next().getAuthority();
		if (role.equals("ADMIN")) {
			return ResponseEntity.ok(usersRepository.findAll());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    public ResponseEntity<User> getUserByToken(HttpServletRequest request) {
		String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        String email = jwtService.getUsernameFromToken(token);
		Optional<User> optionalUser = usersRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(optionalUser.get());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}