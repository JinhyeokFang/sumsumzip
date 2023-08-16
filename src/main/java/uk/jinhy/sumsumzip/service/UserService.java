package uk.jinhy.sumsumzip.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.entity.User;
import uk.jinhy.sumsumzip.entity.UserFollows;
import uk.jinhy.sumsumzip.repository.UserFollowsRepository;
import uk.jinhy.sumsumzip.repository.UserRepository;
import uk.jinhy.sumsumzip.util.JwtProvider;
import uk.jinhy.sumsumzip.util.JwtType;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserFollowsRepository userFollowsRepository;
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

    public User getUserById(Long userId) throws Exception {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new Exception("잘못된 아이디입니다.");
        }

        return user.get();
    }

    public User getUserByEmail(String email) throws Exception {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new Exception("잘못된 이메일입니다.");
        }

        return user.get();
    }

    public void follow(Long followerId, Long followingId) {
        var followUser = userRepository.findById(followerId).get();
        var followingUser = userRepository.findById(followingId).get();
        var follow = userFollowsRepository.findByFollowerAndFollowing(followUser, followingUser);
        if (follow.isEmpty()) {
            var newFollow = UserFollows.builder()
                    .follower(followUser)
                    .following(followingUser)
                    .build();
            userFollowsRepository.save(newFollow);
        }
    }

    public void unfollow(Long followerId, Long followingId) {
        var followUser = userRepository.findById(followerId).get();
        var followingUser = userRepository.findById(followingId).get();
        userFollowsRepository.deleteAllByFollowerAndFollowing(followUser, followingUser);
    }
}
