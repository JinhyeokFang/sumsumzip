package uk.jinhy.sumsumzip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.jinhy.sumsumzip.entity.Cat;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByUserId(Long userId);
    List<Cat> findByIdBetween(Long start, Long end);
}
