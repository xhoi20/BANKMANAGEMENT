package Service;

import Entity.Customer;
import Repository.ICustomerRepository;

import Service.Interfaces.ICustomerService;
import jakarta.transaction.Transactional;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CostumerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    @Value("${jasypt.encryptor.password}")
    private String encryptionKey;

    private static final String ENCRYPTION_ALGORITHM = "PBEWithMD5AndDES";
    @Transactional
    public Customer registerCustomer(String name, String email, String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be set empty");
        }

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(encryptionKey);
        encryptor.setAlgorithm(ENCRYPTION_ALGORITHM);
        String encryptedPassword = encryptor.encrypt(rawPassword);

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(encryptedPassword);

        return customerRepository.save(customer);
    }
    @Transactional
    public Customer loginCustomer(String email, String inputPassword) {
        Customer customer = customerRepository.findByEmail(email);


        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(encryptionKey);
        encryptor.setAlgorithm(ENCRYPTION_ALGORITHM);

        String decryptedPassword = encryptor.decrypt(customer.getPassword());

        if (!decryptedPassword.equals(inputPassword)) {
            throw new RuntimeException("Invalid credentials");
        }

        return customer;
    }
    @Transactional
    public Optional<Customer>getCustomerById(Long id) {
        return customerRepository.findById(id);
    }
    @Transactional
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    @Transactional
    public Customer updateCustomer(Long id,String email, String name, String password) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (!existingCustomer.isPresent()) {
            throw new RuntimeException("Perdoruesi me ID " + id + " nuk u gjet.");
        }
        if (customerRepository.existsByEmail(email) && !existingCustomer.get().getEmail().equals(email)) {
            throw new RuntimeException("Email Already Exists");
        }
        if (customerRepository.existsByName(name)&& !existingCustomer.get().getName().equals(name)) {

            throw new RuntimeException("Name Already Exists");
        }
        Customer customer = existingCustomer.get();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        return customerRepository.save(customer);
    }
    @Transactional
    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Perdoruesi me ID " + id + " nuk u gjet.");
        }
        customerRepository.deleteById(id);
    }
}
