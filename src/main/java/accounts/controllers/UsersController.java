package accounts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import accounts.dtos.AccountDto;
import accounts.dtos.PaymentDto;
import accounts.dtos.UserResponseDto;
import accounts.model.Account;
import accounts.model.User;
import accounts.repositories.UsersRepository;
import accounts.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	UsersService usersService;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(HttpServletRequest request) {
		return usersService.findAll(request);
	}

	@PostMapping("/linkNewAccount")
	public ResponseEntity<User> linkNewAccount(HttpServletRequest request, @RequestBody AccountDto dto) {
		return usersService.linkNewAccount(request, dto);
	}

	@PostMapping("/linkAccount/{accountId}")
	public ResponseEntity<User> linkAccount(HttpServletRequest request, @PathVariable Integer accountId) {
		return usersService.linkAccount(request, accountId);
	}

	@GetMapping("/byToken")
	public ResponseEntity<UserResponseDto> getUserByToken(HttpServletRequest request) {
		return usersService.getUserByToken(request);
	}

	@GetMapping("/canStartRide")
	public ResponseEntity<Boolean> canStartRide(HttpServletRequest request) {
		return usersService.canStartRide(request);
	}

	@PostMapping("/payService")
	public ResponseEntity<Account> payService(HttpServletRequest request, @RequestBody PaymentDto dto) {
		return usersService.payService(request, dto);
	}

	@GetMapping("/getRole")
	public ResponseEntity<String> getRole(HttpServletRequest request) {
		return usersService.getRole(request);
	}
}