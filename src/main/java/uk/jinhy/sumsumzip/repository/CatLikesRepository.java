package uk.jinhy.sumsumzip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.jinhy.sumsumzip.entity.Cat;
import uk.jinhy.sumsumzip.entity.CatLikes;
import uk.jinhy.sumsumzip.entity.User;

import java.util.List;
import java.util.Optional;

public interface CatLikesRepository extends JpaRepository<CatLikes, Long> {
    Optional<CatLikes> findByUserAndCat(User user, Cat cat);
    void deleteAllByUserAndCat(User user, Cat cat);
    List<CatLikes> findByUser(User user);
}
