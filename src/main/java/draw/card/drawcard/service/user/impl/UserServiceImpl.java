package draw.card.drawcard.service.user.impl;

import draw.card.drawcard.domain.User;
import draw.card.drawcard.dto.user.UserDetailResDTO;
import draw.card.drawcard.repository.user.UserRepository;
import draw.card.drawcard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void login(String address) {
        User user = userRepository.findByAddress(address)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAddress(address);
                    // Perform any other necessary initialization for the new user
                    userRepository.save(newUser);
                    return newUser; // 수정: User 객체를 반환하도록 수정
                });

    }

    @Override
    public UserDetailResDTO getUserDetail(String userAddress) {
        Optional<User> userOptional = userRepository.findByAddress(userAddress);
        User user = userOptional.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        UserDetailResDTO userDetail = new UserDetailResDTO();
        userDetail.setName(user.getName());
        userDetail.setProfileImg(user.getProfileImg());

        return userDetail;
    }
}
