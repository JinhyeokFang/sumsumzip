package uk.jinhy.sumsumzip.service;

import lombok.Getter;
import uk.jinhy.sumsumzip.entity.User;

@Getter
public class SessionUser {
    private final String email;
    private final String name;
    public SessionUser(User user) {
        email = user.getEmail();
        name = user.getName();
    }
}
