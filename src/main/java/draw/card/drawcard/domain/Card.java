package draw.card.drawcard.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Card extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String imgURL;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private int price;

    @Column
    private String authorAddress;

    @Column
    private String authorName;

    //이 밑에 부터는 구매했을때 update 됩니다.
    @Column
    private String buyerAddress;

    @Column
    private String buyerName;

    @Column
    private String buyerRank;

    @Column
    private String buyerTeam;

    @Column
    private String nftAddress;

    //0: 판매중, 1: 판매완료
    @Column
    private int status;


}




