package Service;

import Entity.BankAccount;
import Entity.Customer;
import Repository.IAccountRepository;
import Repository.ICustomerRepository;
import Service.Interfaces.IBankAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
@Service
public class BankAccountService implements IBankAccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ICustomerRepository customerRepository;


    @Transactional
    public BankAccount createBankAccount(Long customerId, String accountType, double initialDeposit) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }

        BankAccount account = new BankAccount();
        account.setAccountNumber(UUID.randomUUID().toString().substring(0, 10));
        account.setAccountType(accountType);
        account.setBalance(initialDeposit);
        account.setCustomer(optionalCustomer.get());

        return accountRepository.save(account);
    }

    @Transactional
    public List<BankAccount> viewAccounts(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }



    @Transactional
    public BankAccount depositMoney(Long accountId, double amount) {
        BankAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return account;
    }

    @Transactional
    public BankAccount withdrawMoney(Long accountId, double amount) {
        BankAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setBalance(account.getBalance() - amount);
        return account;
    }
    @Transactional
    public void deleteBankAccountById(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Perdoruesi me ID " + id + " nuk u gjet.");
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public BankAccount updateBankAccount(BankAccount bankaccount) {
        Optional<BankAccount> optionalBankAccount = accountRepository.findById(bankaccount.getId());
        if (!optionalBankAccount.isPresent()) {
            throw new IllegalArgumentException("accounti me ID" + bankaccount.getId() + "nuk gjendet");

        }
        if (bankaccount.getAccountNumber() == null || bankaccount.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Numri i accountit nuk mund te jete bosh");
        }
        if (bankaccount.getAccountType() == null || bankaccount.getAccountType().trim().isEmpty() ) {
            throw new IllegalArgumentException("LLoji i accountit nuk mund te jete bosh");
        }

        BankAccount existingBankAccount = optionalBankAccount.get();
        existingBankAccount.setAccountNumber(bankaccount.getAccountNumber());
        existingBankAccount.setAccountType(bankaccount.getAccountType());
        existingBankAccount.setBalance(bankaccount.getBalance());
        return accountRepository.save(existingBankAccount);
    }
}
