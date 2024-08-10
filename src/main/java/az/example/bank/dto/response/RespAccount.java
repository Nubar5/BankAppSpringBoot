package az.example.bank.dto.response;

import az.example.bank.entity.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespAccount {
    private Long id;

    private String name;
    private String accountNo;
    private String iban;
    private String currency;
    private Double balance;

    private RespCustomer respCustomer;
}
