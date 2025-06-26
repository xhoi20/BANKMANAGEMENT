package Controller;

import Entity.BankAccount;
import Entity.Customer;
import Service.Interfaces.IBankAccountService;
import Service.Interfaces.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    @Autowired
    private IBankAccountService bankAccountService;


@PostMapping("/create")
public ResponseEntity<BankAccount> createBankAccount(@RequestBody @Valid BankAccount bankAccount) {
    try {
        BankAccount account = bankAccountService.createBankAccount(
               // bankAccount.getCustomer() != null ? bankAccount.getCustomer().getId() : null,

                bankAccount.getCustomer().getId(),
                bankAccount.getAccountType(),
                bankAccount.getInitialDeposit()
        );
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BankAccount>> viewAccounts(@PathVariable Long customerId) {
        List<BankAccount> accounts = bankAccountService.viewAccounts(customerId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("{accountId}/deposit")
    public ResponseEntity<BankAccount> depositMoney(
            @PathVariable Long accountId,
            @RequestParam double amount) {
        BankAccount account = bankAccountService.depositMoney(accountId, amount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("{accountId}/withdraw")
    public ResponseEntity<BankAccount> withdrawMoney(
            @PathVariable Long accountId,
            @RequestParam double amount) {
        BankAccount account = bankAccountService.withdrawMoney(accountId, amount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccountById(@PathVariable Long id) {
        bankAccountService.deleteBankAccountById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccount> updateBankAccount(
            @PathVariable Long id,
            @RequestBody BankAccount bankAccount) {
        bankAccount.setId(id);
        BankAccount updatedAccount = bankAccountService.updateBankAccount(bankAccount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException1.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException1 ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

class ResourceNotFoundException1 extends RuntimeException {
    public ResourceNotFoundException1(String message) {
        super(message);
    }

}

