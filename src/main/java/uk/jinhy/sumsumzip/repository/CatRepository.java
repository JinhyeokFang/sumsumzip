package uk.jinhy.sumsumzip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.jinhy.sumsumzip.entity.Cat;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Page<Cat> findAllByUserId(Pageable pageable, Long userId);
}
