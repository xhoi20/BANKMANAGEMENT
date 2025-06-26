package Repository;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"Entity"})
@EnableJpaRepositories(basePackages = {"Repository"})
@ComponentScan(basePackages = {"Controller","Service"})
@EnableEncryptableProperties
public class BankManagement {

    public static void main(String[] args) {
        SpringApplication.run(BankManagement.class, args);
    }
}
