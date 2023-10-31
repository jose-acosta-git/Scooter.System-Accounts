package accounts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accounts.dtos.AccountDto;
import accounts.model.Account;
import accounts.repositories.AccountsRepository;
import accounts.services.AccountsService;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@Autowired
	AccountsRepository accountsRepository;
	@Autowired
	AccountsService accountsService;
	
	@PostMapping
	public Account create(AccountDto dto) {
		return accountsService.save(dto);
	}
	
	@GetMapping
	public List<Account> findAll() {
		return accountsRepository.findAll();
	}
	
}
