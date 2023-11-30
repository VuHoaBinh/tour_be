package sf.travel.rests.types;

import javax.validation.constraints.NotNull;
import lombok.Data;
import sf.travel.enums.Status;

@Data
public class CreateOrderReq {
    @NotNull
    private Long travelId;
    @NotNull
    private String name;
    @NotNull
    private String email;
    private String title;
    private String description;
}
