package draw.card.drawcard.dto.card;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorCardResDTO {
    private String title;
    private String imgURL;
    private int price;
    private Long id;
}
