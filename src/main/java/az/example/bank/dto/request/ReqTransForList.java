package az.example.bank.dto.request;

import lombok.Data;

@Data
public class ReqTransForList {
    private String cif;
    private Long accountId;
}
