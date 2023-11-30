package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.Status;

@Data
public class UpdateOrderReq {
    private Status status;
}
