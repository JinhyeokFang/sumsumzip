package uk.jinhy.sumsumzip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.jinhy.sumsumzip.entity.User;
import uk.jinhy.sumsumzip.entity.UserFollows;

import java.util.Optional;

public interface UserFollowsRepository extends JpaRepository<UserFollows, Long> {
    Optional<UserFollows> findByFollowerAndFollowing(User follower, User following);
    Long deleteAllByFollowerAndFollowing(User follower, User following);

    Long countUserFollowsByFollowing_Id(Long followingId);
}
