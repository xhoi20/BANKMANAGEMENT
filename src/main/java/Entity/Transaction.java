package Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="[Transaction]")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="type")
    private String type;
    @Column(name="amount")
    private double amount;
    @Column(name= "order_date", nullable = false)
    private LocalDateTime date;
    @Column(name="fromAccount")
    private String fromAccount;
    @Column(name="toAccount")
    private String toAccount;
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @JsonBackReference
    private BankAccount bankAccount;
    @Override
    public String toString() {
        return date + " - " + type + " - " + amount +
                (fromAccount != null ? " from " + fromAccount : "") +
                (toAccount != null ? " to " + toAccount : "");
    }

}
