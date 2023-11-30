package sf.travel.rests.types;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserFilter extends PaginationParams{
    private String name;
    private String email;
}
