package Service.Interfaces;

import Entity.BankAccount;
import Entity.Customer;

import java.util.List;
import java.util.Optional;

public interface IBankAccountService {

    BankAccount createBankAccount(Long customerId, String accountType, double initialDeposit);

    List<BankAccount> viewAccounts(Long customerId);
    BankAccount depositMoney(Long accountId, double amount);
    BankAccount withdrawMoney(Long accountId, double amount);
    void deleteBankAccountById(Long id);
    BankAccount updateBankAccount(BankAccount bankAccount);
}