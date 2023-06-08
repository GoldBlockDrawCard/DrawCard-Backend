package draw.card.drawcard.service.user;

import draw.card.drawcard.dto.user.UserDetailResDTO;

public interface UserService {
    public void login(String userAddress);
    public UserDetailResDTO getUserDetail(String userAddress);
}
