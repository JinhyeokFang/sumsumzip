package uk.jinhy.sumsumzip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.jinhy.sumsumzip.entity.Cat;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Page<Cat> findAllByUserId(Pageable pageable, Long userId);

    @Query("SELECT c FROM Cat c WHERE c.user.Id IN (:ids)")
    Page<Cat> findByListOfUserId(Pageable pageable, List<Long> ids);
}
