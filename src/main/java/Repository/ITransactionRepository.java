package Repository;
import Entity.BankAccount;
import Entity.Transaction;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByFromAccountOrToAccount(String fromAccountNum, String toAccountNum);
   // List<Transaction> findByCustomerId(Long customerId);
}
