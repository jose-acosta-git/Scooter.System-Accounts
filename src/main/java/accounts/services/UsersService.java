package accounts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accounts.dtos.UserDto;
import accounts.model.User;
import accounts.repositories.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	public User save(UserDto dto) {
		return usersRepository.save(convertToEntity(dto));
	}
	
	private User convertToEntity(UserDto dto) {
		return new User(dto.getName(), dto.getEmail(), dto.getPhone(), dto.getRole());
	}

}
