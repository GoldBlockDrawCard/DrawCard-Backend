package draw.card.drawcard.dto.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardReqDTO {

    private String title;
    private String description;
    private String category;
    private int price;

}
