package accounts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping
	public User create(@RequestBody UserDto dto) {
		return usersService.save(dto);
	}
	
	@GetMapping
	public List<User> findAll() {
		return usersRepository.findAll();
	}

}
