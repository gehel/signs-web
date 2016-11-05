package ch.ledcom.signs;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
public class SignResource extends ResourceSupport {

    private final String name;
    private final String description;
    private final String image;

    public static SignResource from(Sign sign) {
        return new SignResource(sign.getName(), sign.getDescription(), sign.getImage());
    }
}
