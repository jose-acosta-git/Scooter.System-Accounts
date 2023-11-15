package accounts.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import accounts.dtos.AccountDto;
import accounts.model.Account;
import accounts.model.User;
import accounts.repositories.AccountsRepository;
import accounts.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private AccountsRepository accountsRepository;

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
}