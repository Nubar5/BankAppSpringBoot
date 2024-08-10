package az.example.bank.dto.request;

import az.example.bank.dto.response.RespAccount;
import az.example.bank.dto.response.RespCustomer;
import lombok.Data;

@Data
public class ReqTransaction {
    private Long id;
    private String crAccount;
    private String currency;
    private Double amount;
    private Long dtReqAccountId;
    private Long dtReqCustomerId;
}
