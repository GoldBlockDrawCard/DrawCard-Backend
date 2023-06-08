package draw.card.drawcard.dto.card;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardDetailResDTO {
    private String title;
    private String description;
    private String category;
    private int price;
    private String authorAddress;
    private String imgURL;
    private String authorName;
    private String status;
}
