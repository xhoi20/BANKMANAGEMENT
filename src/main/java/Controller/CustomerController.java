

package Controller;

import Entity.Customer;
import Service.Interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerService.registerCustomer(
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPassword()
            );
            return ResponseEntity.ok(createdCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Customer> loginCustomer(@RequestBody Customer customer) {
        try {
            Customer authenticatedCustomer = customerService.loginCustomer(
                    customer.getEmail(),
                    customer.getPassword()
            );
            return ResponseEntity.ok(authenticatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("id")
    public ResponseEntity<Customer> getCustomerById(@RequestParam Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(
                    id,
                    customer.getEmail(),
                    customer.getName(),
                    customer.getPassword()
            );
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundExceptionC.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundExceptionC ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

class ResourceNotFoundExceptionC extends RuntimeException {
    public ResourceNotFoundExceptionC(String message) {
        super(message);
    }
}
