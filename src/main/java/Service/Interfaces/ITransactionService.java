package Service.Interfaces;

import Entity.Transaction;

import java.util.List;

public interface ITransactionService {
    Transaction transferFunds(String fromAccount, String toAccount, double amount);
    List<Transaction> viewTransactionHistory(String accountNumber);
   // List<Transaction> viewTransaction(Long customerId);
    void deleteTransactionById(Long id);
}
