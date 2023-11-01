package accounts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PatchMapping("/{accountId}/addMoney/{moneyCount}")
	public ResponseEntity<Account> addMoney(@PathVariable int accountId, @PathVariable double moneyCount) {
		Account updatedAccount = accountsService.addMoney(accountId, moneyCount);
        if (updatedAccount == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAccount);
	}
	
	@PostMapping
	public Account create(@RequestBody AccountDto dto) {
		return accountsService.save(dto);
	}
	
	@GetMapping
	public List<Account> findAll() {
		return accountsRepository.findAll();
	}
	
	@PatchMapping("/{accountId}/linkUser/{userId}")
	public ResponseEntity<Account> linkUser(@PathVariable int accountId, @PathVariable int userId) {
		Account linkedAccount = accountsService.linkUser(accountId, userId);
		if (linkedAccount == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(linkedAccount);
	}
	
	@PatchMapping("/{accountId}/deactivate")
	public ResponseEntity<Account> deactiveAccount(@PathVariable int accountId) {
		return accountsService.deactivate(accountId);
	}
	
	@PatchMapping("/{accountId}/activate")
	public ResponseEntity<Account> activate(@PathVariable int accountId) {
		return accountsService.activate(accountId);
	}
	
}
