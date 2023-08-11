package uk.jinhy.sumsumzip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.repository.UserRepository;
import uk.jinhy.sumsumzip.util.JwtProvider;
import uk.jinhy.sumsumzip.util.JwtType;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public String createAccessToken(String refreshToken) throws Exception{
        var claims = jwtProvider.parseJwtToken(refreshToken);
        if (
                !claims.getSubject().equals(
                        JwtType.REFRESH_TOKEN.toString()
                )
        ) {
            throw new Exception("Refresh Token이 아닙니다.");
        }
        return jwtProvider.createToken(
                JwtType.ACCESS_TOKEN,
                (String) claims.get("email")
        );
    }

    public Long getUserIdByEmail(String email) throws Exception {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new Exception("잘못된 이메일입니다.");
        }

        return user.get().getId();
    }
}
