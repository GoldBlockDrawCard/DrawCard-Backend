package draw.card.drawcard.controller;

import draw.card.drawcard.dto.user.UserLoginReqDTO;
import draw.card.drawcard.service.card.CardService;
import draw.card.drawcard.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    private final UserService userService;
    private final CardService cardService;

    @Autowired
    public UserController(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLoginReqDTO userLoginReqDTO){

        String address=userLoginReqDTO.getUserAddress();

        //db에 저장
        userService.login(address);
        String token = generateToken(userLoginReqDTO.getUserAddress());
        return ResponseEntity.ok().body(token);
    }

    private String generateToken(String address) {
        String token = Jwts.builder()
                .setSubject(address)
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey")
                .compact();
        return token;
    }

    @GetMapping("/cards/{userAddress}")
    public ResponseEntity<Object> getCardLists(@PathVariable String userAddress) {
        Map<String,Object> response=new HashMap<>();
        response.put("userDetail",userService.getUserDetail(userAddress));
        response.put("onSaleCardList",cardService.getOnSaleCardList(userAddress));
        response.put("purchasedCardList",cardService.getPurchasedCardList(userAddress));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{cardId}")
    public ResponseEntity<Object> getCardDetail(@PathVariable int cardId) {

        Map<String,Object> response=new HashMap<>();
        response.put("cardDetail",cardService.getCardDetail(cardId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
