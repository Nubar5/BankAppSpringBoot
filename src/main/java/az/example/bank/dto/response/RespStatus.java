package az.example.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespStatus {
    private Integer status;
    private String msg;


    public static RespStatus getSuccessMsg(){
       return new RespStatus(1,"success");
    }
}
