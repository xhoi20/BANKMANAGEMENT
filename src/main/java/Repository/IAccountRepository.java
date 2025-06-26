package Repository;


import Entity.BankAccount;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAccountRepository extends CrudRepository<BankAccount, Long> {
    List<BankAccount> findByCustomerId(Long customerId);

}

