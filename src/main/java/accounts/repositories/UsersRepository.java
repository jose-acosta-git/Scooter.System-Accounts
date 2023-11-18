package accounts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import accounts.model.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}