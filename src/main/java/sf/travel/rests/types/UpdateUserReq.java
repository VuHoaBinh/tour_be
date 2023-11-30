package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.Role;

@Data
public class UpdateUserReq {
    private Role role;
}
