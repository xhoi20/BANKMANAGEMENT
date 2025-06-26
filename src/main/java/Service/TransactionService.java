package Service;

import Entity.BankAccount;
import Entity.Transaction;
import Repository.IAccountRepository;
import Repository.ITransactionRepository;
import Service.Interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private ITransactionRepository transactionRepository;
@Transactional
    public Transaction transferFunds(String fromAccount, String toAccount, double amount) {
        Transaction transaction = Transaction.builder()
                .type("TRANSFER")
                .amount(amount)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .date(LocalDateTime.now())
                .build();
        return transactionRepository.save(transaction);
    }

    public List<Transaction> viewTransactionHistory(String accountNumber) {
        return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
    }
//    @Transactional
//
//    public List<Transaction> viewTransaction(Long customerId) {
//        return transactionRepository.findByCustomerId(customerId);
//    }
@Transactional

public void deleteTransactionById(Long id) {
    if (!transactionRepository.existsById(id)) {
        throw new RuntimeException("Perdoruesi me ID " + id + " nuk u gjet.");
    }
    transactionRepository.deleteById(id);
}

}