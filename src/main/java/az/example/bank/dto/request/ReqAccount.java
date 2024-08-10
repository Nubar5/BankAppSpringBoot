package az.example.bank.dto.request;

import az.example.bank.dto.response.RespCustomer;
import lombok.Data;

@Data
public class ReqAccount {
    private Long id;

    private String name;
    private String accountNo;
    private String iban;
    private String currency;
    private Double balance;

    private Long customerId;
}
