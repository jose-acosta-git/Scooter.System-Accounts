package accounts.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
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
import accounts.dtos.PaymentDto;
import accounts.model.Account;
import accounts.repositories.AccountsRepository;
import accounts.services.AccountsService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@Autowired
	AccountsRepository accountsRepository;
	@Autowired
	AccountsService accountsService;
	
	@PostMapping("/{accountId}/addMoney/{moneyCount}")
	public ResponseEntity<Account> addMoney(HttpServletRequest request, @PathVariable int accountId, @PathVariable double moneyCount) {
		return accountsService.addMoney(request, accountId, moneyCount);
	}
	
	@PostMapping
	public Account create(@RequestBody AccountDto dto) {
		return accountsService.save(dto);
	}
	
	@GetMapping
	public ResponseEntity<List<Account>> findAll(HttpServletRequest request) {
		return accountsService.findAll(request);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<Account> getById(HttpServletRequest request, @PathVariable int accountId) {
		return accountsService.getById(request, accountId);
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
	public ResponseEntity<Account> deactiveAccount(HttpServletRequest request, @PathVariable int accountId) {
		return accountsService.deactivate(request, accountId);
	}
	
	@PatchMapping("/{accountId}/activate")
	public ResponseEntity<Account> activate(HttpServletRequest request, @PathVariable int accountId) {
		return accountsService.activate(request, accountId);
	}
	
}
