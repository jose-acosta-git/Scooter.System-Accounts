package accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import accounts.model.User;

public interface UsersRepository extends JpaRepository<User, Integer> {

}
