package draw.card.drawcard.service.card.impl;

import draw.card.drawcard.domain.Card;
import draw.card.drawcard.domain.User;
import draw.card.drawcard.dto.card.*;
import draw.card.drawcard.repository.card.CardRepository;
import draw.card.drawcard.repository.user.UserRepository;
import draw.card.drawcard.service.card.CardService;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final IPFS ipfs;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    public CardDetailResDTO getCardDetail(int cardId) {
        Card card = cardRepository.getCardDetail(cardId);

        CardDetailResDTO cardDetail = new CardDetailResDTO();
        cardDetail.setTitle(card.getTitle());
        cardDetail.setDescription(card.getDescription());
        cardDetail.setCategory(card.getCategory());
        cardDetail.setPrice(card.getPrice());
        cardDetail.setAuthorAddress(card.getAuthorAddress());
        cardDetail.setImgURL(card.getImgURL());
        cardDetail.setAuthorName(card.getAuthorName());

        return cardDetail;
    }

    @Override
    public List<OnSaleCardListDTO> getOnSaleCardList(String userAddress) {
        List<Card> onSaleCards = cardRepository.findOnSaleCardsByAuthorAddress(userAddress);

        List<OnSaleCardListDTO> onSaleCardList = new ArrayList<>();
        for (Card card : onSaleCards) {
            OnSaleCardListDTO onSaleCard = new OnSaleCardListDTO();
            onSaleCard.setAuthorName(card.getAuthorName());
            onSaleCard.setTitle(card.getTitle());
            onSaleCard.setImgURL(card.getImgURL());
            onSaleCardList.add(onSaleCard);
        }

        return onSaleCardList;
    }

    @Override
    public List<PurchasedCardListDTO> getPurchasedCardList(String userAddress) {
        List<Card> purchasedCards = cardRepository.findPurchasedCardsByBuyerAddress(userAddress);

        List<PurchasedCardListDTO> purchasedCardList = new ArrayList<>();
        for (Card card : purchasedCards) {
            PurchasedCardListDTO purchasedCard = new PurchasedCardListDTO();
            purchasedCard.setAuthorName(card.getAuthorName());
            purchasedCard.setTitle(card.getTitle());
            purchasedCard.setImgURL(card.getImgURL());
            purchasedCardList.add(purchasedCard);
        }

        return purchasedCardList;
    }

    @Override
    public Map<String, List<AuthorCardResDTO>> getAllAuthorCards() {

        List<Card> cards = cardRepository.findAll();

        Map<String, List<AuthorCardResDTO>> authorCardsMap = new HashMap<>();

        for (Card card : cards) {
            String authorAddress = card.getAuthorAddress();
            String authorName = card.getAuthorName();

            AuthorCardResDTO authorCard = new AuthorCardResDTO();
            authorCard.setTitle(card.getTitle());
            authorCard.setImgURL(card.getImgURL());
            authorCard.setPrice(card.getPrice());
            authorCard.setId(card.getId());

            if (authorCardsMap.containsKey(authorAddress)) {
                List<AuthorCardResDTO> authorCards = authorCardsMap.get(authorAddress);
                authorCards.add(authorCard);
            } else {
                List<AuthorCardResDTO> authorCards = new ArrayList<>();
                authorCards.add(authorCard);
                authorCardsMap.put(authorAddress, authorCards);
            }
        }

        return authorCardsMap;
    }

    @Override
    public Map<String, List<CategoryCardResDTO>> getCardsByCategory() {

        List<Card> cards = cardRepository.findAll();

        Map<String, List<CategoryCardResDTO>> categoryCardsMap = new HashMap<>();

        for (Card card : cards) {
            String category = card.getCategory();

            if (!categoryCardsMap.containsKey(category)) {
                categoryCardsMap.put(category, new ArrayList<>());
            }

            CategoryCardResDTO categoryCard = new CategoryCardResDTO();
            categoryCard.setTitle(card.getTitle());
            categoryCard.setImgURL(card.getImgURL());
            categoryCard.setPrice(card.getPrice());
            categoryCard.setId(card.getId());

            List<CategoryCardResDTO> categoryCards = categoryCardsMap.get(category);
            categoryCards.add(categoryCard);
        }

        return categoryCardsMap;
    }



    public void registerCard(CardReqDTO cardReqDTO, MultipartFile file,String authorAddress) throws IOException {
        if (file != null && !file.isEmpty()) {
            Card card = new Card();
            // IPFS에 이미지 업로드 및 URL 반환
            String imageUrl = uploadImageToIPFS(file);
            card.setImgURL(imageUrl);
            card.setTitle(cardReqDTO.getTitle());
            card.setDescription(cardReqDTO.getDescription());
            card.setCategory(cardReqDTO.getCategory());
            card.setPrice(cardReqDTO.getPrice());
            card.setAuthorAddress(authorAddress);
            Optional<User> authorOptional = userRepository.findByAddress(authorAddress);
            User author = authorOptional.orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다."));
            //명함의 작가는 자동으로 판매자로 등록
            author.setStatus("1");
            userRepository.save(author);
            String authorName = author.getName();
            card.setAuthorName(authorName);
            cardRepository.save(card);
        }

    }



    private String uploadImageToIPFS(MultipartFile file) throws IOException {
        NamedStreamable.InputStreamWrapper inputStreamWrapper = new NamedStreamable.InputStreamWrapper(file.getInputStream());
        CompletableFuture<MerkleNode> future = (CompletableFuture<MerkleNode>) ipfs.add(inputStreamWrapper);
        MerkleNode result = future.join();

        return "https://ipfs.io/ipfs/" + result.hash.toString();
    }
}
