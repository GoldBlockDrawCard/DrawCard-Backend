package draw.card.drawcard.controller;

import draw.card.drawcard.domain.Card;
import draw.card.drawcard.dto.card.CardReqDTO;
import draw.card.drawcard.service.card.CardService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CardController {
    private CardService cardService;
    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<String> registerCard(@RequestParam("file") MultipartFile file, @RequestBody CardReqDTO cardReqDTO, HttpServletRequest request) {
        String userAddress = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws((request.getHeader("Authorization")).substring(7)).getBody()).getSubject();

        try {
            if (file != null && !file.isEmpty()) {
                // 파일이 제대로 전송된 경우에만 카드 등록

                cardService.registerCard(cardReqDTO, file,userAddress);
            } else {
                return ResponseEntity.badRequest().body("파일을 선택해주세요.");
            }

            return ResponseEntity.ok("카드 등록이 완료되었습니다.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카드 등록 중 오류가 발생했습니다.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<Object> getCardDetail(@PathVariable int cardId) {

        Map<String,Object> response=new HashMap<>();
        response.put("cardDetail",cardService.getCardDetail(cardId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
