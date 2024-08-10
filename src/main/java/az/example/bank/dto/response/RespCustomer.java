package az.example.bank.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
@Data
@Builder
public class RespCustomer {
    private Long id;

    private String name;
    private String surname;
    private String mobile;
    private String email;
    private String address;
    private String seria;
    private String cif;


}
