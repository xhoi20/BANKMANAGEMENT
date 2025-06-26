package Service.Interfaces;
import Entity.Customer;

import java.util.Optional;
public interface ICustomerService {
    Customer registerCustomer(String name, String email, String rawPassword);
    Customer loginCustomer(String email, String inputPassword);
    Optional<Customer> getCustomerById(Long id);
    Iterable<Customer> getAllCustomers();
    Customer updateCustomer(Long id, String email, String name, String password);
    void deleteCustomerById(Long id);
}
