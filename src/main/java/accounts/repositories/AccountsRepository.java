package accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import accounts.model.Account;

public interface AccountsRepository extends JpaRepository<Account, Integer> {}