package accounts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import accounts.dtos.AccountDto;
import accounts.dtos.UserDto;
import accounts.model.User;
import accounts.repositories.UsersRepository;
import accounts.services.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	UsersService usersService;
	
	@GetMapping
	public List<User> findAll() {
		return usersRepository.findAll();
	}

	@PostMapping("/{userId}/linkNewAccount")
	public ResponseEntity<User> linkNewAccount(@PathVariable Integer userId, @RequestBody AccountDto dto) {
		return usersService.linkNewAccount(userId, dto);
	}

	@PostMapping("/{userId}/linkAccount/{accountId}")
	public ResponseEntity<User> linkAccount(@PathVariable Integer userId, @PathVariable Integer accountId) {
		return usersService.linkAccount(userId, accountId);
	}

}
