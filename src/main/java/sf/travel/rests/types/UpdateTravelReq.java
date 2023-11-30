package sf.travel.rests.types;

import lombok.Data;
import sf.travel.enums.TravelDomainType;
import sf.travel.enums.TravelType;

@Data
public class UpdateTravelReq {
    private String name;
    private String description;
    private TravelType type;
    private Integer price;
    private String image;
    private TravelDomainType domain;
}
