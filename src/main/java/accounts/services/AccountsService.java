package accounts.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import accounts.config.JwtAuthenticationFilter;
import accounts.dtos.AccountDto;
import accounts.dtos.PaymentDto;
import accounts.model.Account;
import accounts.model.User;
import accounts.repositories.AccountsRepository;
import accounts.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AccountsService {

	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;
	
	public Account save(AccountDto dto) {
		return accountsRepository.save(convertToEntity(dto));
	}
	
	private Account convertToEntity(AccountDto dto) {
		return new Account(dto.getRegistrationDate(), dto.getBalance(), dto.getMercadoPagoId());
	}

	public ResponseEntity<Account> addMoney(HttpServletRequest request, int accountId, double moneyCount) {
		Optional<Account> optionalAccount = accountsRepository.findById(accountId);
		if (!optionalAccount.isPresent())
			return ResponseEntity.notFound().build();
		Account account = optionalAccount.get();

		String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        String email = jwtService.getUsernameFromToken(token);
        User user = usersRepository.findByEmail(email).get();

		if (!user.getAccounts().contains(account))
			return ResponseEntity.badRequest().build();
		
		/*
			* Antes de sumar el dinero la app debería comunicarse con una API de MercadoPago, para
			* restar el dinero de MercadoPago antes de sumarlo aquí (luego de verificar que la cuenta
			* de MercadoPago tenga ese dinero disponible)
			* */
		account.addMoney(moneyCount);
		return ResponseEntity.ok(accountsRepository.save(account));
	}

	public Account linkUser(int accountId, int userId) {
		Optional<Account> optionalAccount = accountsRepository.findById(accountId);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			Optional<User> optionalUser = usersRepository.findById(userId);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				account.addUser(user);
				return accountsRepository.save(account);
			}
			return null;
		}
		return null;
	}

	public ResponseEntity<Account> deactivate(int id) {
		Optional<Account> optionalAccount = accountsRepository.findById(id);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			account.deactivate();
			return ResponseEntity.ok(accountsRepository.save(account));
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Account> activate(int id) {
		Optional<Account> optionalAccount = accountsRepository.findById(id);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			account.activate();
			return ResponseEntity.ok(accountsRepository.save(account));
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Account> getById(int accountId) {
		Optional<Account> optionalAccount = accountsRepository.findById(accountId);
		if (optionalAccount.isPresent()) {
			return ResponseEntity.ok(optionalAccount.get());
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Account> payService(int accountId, PaymentDto dto) {
		Optional<Account> optionalAccount = accountsRepository.findById(accountId);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			account.payService(dto.getPrice());
			return ResponseEntity.ok(accountsRepository.save(account));
		}
		return ResponseEntity.notFound().build();
	}
	
}
