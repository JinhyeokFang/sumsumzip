package uk.jinhy.sumsumzip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long getUserIdByEmail(String email) throws Exception {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new Exception("잘못된 이메일입니다.");
        }

        return user.get().getId();
    }
}
