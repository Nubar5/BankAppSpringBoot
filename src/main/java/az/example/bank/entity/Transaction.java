package az.example.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity
@Table(name = "transactions")
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "dtAccountId")
    private Account dtAccount;
    private String crAccount;
    private String currency;
    private Double amount;
    @CreationTimestamp
    private Date trDate;
    @OneToOne
    @JoinColumn(name="customerId")
    private Customer dtCustomer;
    @ColumnDefault(value = "1")
    private Integer active;

}
