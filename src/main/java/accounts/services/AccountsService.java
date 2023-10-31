package accounts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accounts.dtos.AccountDto;
import accounts.model.Account;
import accounts.repositories.AccountsRepository;

@Service
public class AccountsService {

	@Autowired
	private AccountsRepository accountsRepository;
	
	public Account save(AccountDto dto) {
		return accountsRepository.save(convertToEntity(dto));
	}
	
	private Account convertToEntity(AccountDto dto) {
		return new Account(dto.getRegistrationDate(), dto.getBalance(), dto.getMercadoPagoId());
	}
	
}
