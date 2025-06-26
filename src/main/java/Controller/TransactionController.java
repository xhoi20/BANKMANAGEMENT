

package Controller;

import Entity.Customer;
import Entity.Transaction;
import Service.Interfaces.ICustomerService;
import Service.Interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferFunds(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.transferFunds(transaction.getFromAccount(), transaction.getToAccount(), transaction.getAmount());
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    }


    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> viewTransactionHistory(@RequestParam String accountNumber) {
        List<Transaction> transactions = transactionService.viewTransactionHistory(accountNumber);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException3.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException3 ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
class ResourceNotFoundException3 extends RuntimeException {
    public ResourceNotFoundException3(String message) {
        super(message);
    }
}



