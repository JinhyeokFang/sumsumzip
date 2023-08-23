package uk.jinhy.sumsumzip.controller.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.jinhy.sumsumzip.entity.User;

@NoArgsConstructor
@Getter
public class UserDTO {
    private String email;
    private String name;
    private String picture;
    private Long id;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.picture = user.getPicture();
    }
}
