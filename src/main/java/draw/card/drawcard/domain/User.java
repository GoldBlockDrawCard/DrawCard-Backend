package draw.card.drawcard.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(columnDefinition = "varchar(255) default 'Cardrean'")
    private String name;

    @Column
    private String address;

    //0: 일반사용자, 1:작가
    @Column
    private String status;

    @Column
    private String profileImg;



}
