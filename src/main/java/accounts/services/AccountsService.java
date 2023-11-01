package accounts.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accounts.dtos.AccountDto;
import accounts.model.Account;
import accounts.repositories.AccountsRepository;
import jakarta.persistence.EntityNotFoundException;

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

	public Account addMoney(int accountId, double moneyCount) {
		Optional<Account> optionalAccount = accountsRepository.findById(accountId);
		if (optionalAccount.isPresent()) {
			Account account = optionalAccount.get();
			/*
			 * Antes de sumar el dinero la app debería comunicarse con una API de MercadoPago, para
			 * restar el dinero de MercadoPago antes de sumarlo aquí (luego de verificar que la cuenta
			 * de MercadoPago tenga ese dinero disponible)
			 * */
			account.addMoney(moneyCount);
			return accountsRepository.save(account);
		}
		return null;
	}
	
}
