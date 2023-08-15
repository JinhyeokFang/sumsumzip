package uk.jinhy.sumsumzip.controller.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.jinhy.sumsumzip.entity.User;
import uk.jinhy.sumsumzip.entity.UserFollows;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserWithFollowDataDTO extends UserDTO {
    private List<UserDTO> followers;
    private List<UserDTO> following;

    public UserWithFollowDataDTO(User user) {
        super(user);
        this.followers = user
                .getFollowers()
                .stream()
                .map(UserFollows::getFollowing)
                .map(UserDTO::new)
                .toList();
        this.following =  user
                .getFollowing()
                .stream()
                .map(UserFollows::getFollower)
                .map(UserDTO::new)
                .toList();
    }
}
