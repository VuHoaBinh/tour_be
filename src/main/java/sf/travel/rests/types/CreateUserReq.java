package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.Role;

@Data
public class CreateUserReq {
    private String name;
    private String email;
    private String password;
    private Role role;
}
