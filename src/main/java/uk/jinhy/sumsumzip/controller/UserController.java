package uk.jinhy.sumsumzip.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.jinhy.sumsumzip.controller.user.FollowRequestDTO;
import uk.jinhy.sumsumzip.controller.user.TokenResponseDTO;
import uk.jinhy.sumsumzip.controller.user.UserWithFollowDataDTO;
import uk.jinhy.sumsumzip.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final HttpSession httpSession;
    private final UserService userService;

    @GetMapping("/token")
    public TokenResponseDTO token() {
        var refreshToken = (String) httpSession.getAttribute("refresh-token");
        if (refreshToken == null) {
            return new TokenResponseDTO(
                    false,
                    null,
                    null
            );
        }
        try {
            var accessToken = userService.createAccessToken(refreshToken);
            var email = userService.getEmailByToken(refreshToken);
            return new TokenResponseDTO(true, accessToken, email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/{userId}")
    public UserWithFollowDataDTO getUserById(
            @PathVariable Long userId
    ) {
        try {
            return new UserWithFollowDataDTO(
                    userService.getUserById(userId)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user")
    public UserWithFollowDataDTO getUserByToken(
            Authentication authentication
    ) {
        if (authentication == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "토큰이 필요합니다."
            );
        }
        var email = (String) authentication.getPrincipal();
        try {
            return new UserWithFollowDataDTO(
                    userService.getUserByEmail(email)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/user/follow")
    public void follow(
            FollowRequestDTO body,
            Authentication authentication
    ) {
        if (authentication == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "토큰이 필요합니다."
            );
        }
        var email = (String) authentication.getPrincipal();
        try {
            var followerId = userService.getUserIdByEmail(email);
            userService.follow(followerId, body.getFollowingId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/user/unfollow")
    public void unfollow(
            FollowRequestDTO body,
            Authentication authentication
    ) {
        if (authentication == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "토큰이 필요합니다."
            );
        }
        var email = (String) authentication.getPrincipal();
        try {
            var followerId = userService.getUserIdByEmail(email);
            userService.unfollow(followerId, body.getFollowingId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/{userId}/followers/count")
    public Long getNumberOfFollowers(
            @PathVariable("userId") Long userId
    ) {
        return userService.getNumberOfFollowers(userId);
    }
}
