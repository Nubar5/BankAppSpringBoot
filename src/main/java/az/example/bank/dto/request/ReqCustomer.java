package az.example.bank.dto.request;

import lombok.Data;

@Data
public class ReqCustomer {
    private Long id;
    private String name;
    private String surname;
    private String mobile;
    private String email;
    private String address;
    private String seria;
    private String cif;
}
