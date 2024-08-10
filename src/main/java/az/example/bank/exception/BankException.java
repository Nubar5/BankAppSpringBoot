package az.example.bank.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BankException extends RuntimeException{
    private Integer code;
    public BankException(String msg){
        super(msg);
    }
    public BankException(Integer code,String msg){
        super(msg);
        this.code=code;
    }
    public Integer getCode(){
        return code;
    }
}
