package draw.card.drawcard.repository.card;

import draw.card.drawcard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>,CardRepositoryCustom {
    Card getCardDetail(int cardId);

    List<Card> findOnSaleCardsByAuthorAddress(String userAddress);

    List<Card> findPurchasedCardsByBuyerAddress(String userAddress);

    List<Card> findByAuthorAddress(String authorAddress);
}
