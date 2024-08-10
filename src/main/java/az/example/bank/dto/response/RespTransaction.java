package az.example.bank.dto.response;

import az.example.bank.entity.Account;
import az.example.bank.entity.Customer;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Builder
public class RespTransaction {
    private Long id;
    private RespAccount dtRespAccount;
    private String crAccount;
    private String currency;
    private Double amount;
    private RespCustomer dtRespCustomer;
}
