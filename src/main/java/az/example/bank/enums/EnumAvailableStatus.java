package az.example.bank.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum EnumAvailableStatus {
    ACTIVE(1),DEACTIVE(0);

    private int value;
    public int getValue(){
        return value;
    }
}
