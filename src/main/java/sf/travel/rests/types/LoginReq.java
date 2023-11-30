package sf.travel.rests.types;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
