package uk.jinhy.sumsumzip.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users_follows")
@Entity
@NoArgsConstructor
@Getter
public class UserFollows {
    @Id
    @Column(name = "userFollowId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @ManyToOne
    private User follower;

    @ManyToOne
    private User following;

    @Builder
    public UserFollows(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
