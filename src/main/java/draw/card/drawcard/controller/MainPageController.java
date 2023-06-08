package draw.card.drawcard.controller;

import draw.card.drawcard.service.card.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/main")
@CrossOrigin(origins = {"http://localhost:3000"})
public class MainPageController {
    private final CardService cardService;

    @Autowired
    public MainPageController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<Object> getCardDetail() {

        Map<String,Object> response=new HashMap<>();
        response.put("mainDetail",cardService.getAllAuthorCards());
        response.put("categoryDetail",cardService.getCardsByCategory());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
