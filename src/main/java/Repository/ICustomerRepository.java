package Repository;

import Entity.Customer;

import org.springframework.data.repository.CrudRepository;

public interface ICustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);


    boolean existsByEmail(String email);


    Customer findByName(String name);
    boolean existsByName(String name);


    Customer findById(long id);


    boolean existsById(long id);
}
