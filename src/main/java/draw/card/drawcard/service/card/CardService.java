package draw.card.drawcard.service.card;

import draw.card.drawcard.dto.card.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CardService {
    public void registerCard(CardReqDTO cardReqDTO, MultipartFile file,String authorAddress) throws Exception;
    public CardDetailResDTO getCardDetail(int cardId);

    public List<OnSaleCardListDTO> getOnSaleCardList(String userAddress);

    public List<PurchasedCardListDTO> getPurchasedCardList(String userAddress);

    public Map<String, List<AuthorCardResDTO>> getAllAuthorCards();

    public Map<String, List<CategoryCardResDTO>> getCardsByCategory();
}
