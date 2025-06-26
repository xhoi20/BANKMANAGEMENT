package Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table (name="BankAccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="accountNumber")
    private String accountNumber;
    @Column(name="accountType")
    private String accountType;
    @Column(name="balance")
    private double balance;
    @Column(name="InitialDeposit")
    private double initialDeposit;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)

    @JsonManagedReference
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();
    @Override
    public String toString() {
        return "BankAccount [accountNumber=" + accountNumber + ", accountType=" + accountType + ", balance=" + balance + "]";
    }

}
