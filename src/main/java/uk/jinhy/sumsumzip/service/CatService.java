package uk.jinhy.sumsumzip.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.entity.Cat;
import uk.jinhy.sumsumzip.repository.CatRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CatService {
    private final CatRepository catRepository;
    public void addCat(String url, Long userId, String title, String description) {
        var cat = Cat.builder()
                .url(url)
                .userId(userId)
                .title(title)
                .description(description)
                .build();
        catRepository.save(cat);
    }

    public void deleteCat(Long catId) {
        var cat = catRepository.findById(catId);
        cat.ifPresent(catRepository::delete);
    }

    private Long getNumberOfLastCat() { return catRepository.count(); }

    public List<Cat> getCats(Long start, Long end) {
        return catRepository.findByIdBetween(start, end);
    }
    public List<Cat> getCats() {
        Long end = getNumberOfLastCat();
        Long start = end - 5 + 1;

        return getCats(start, end);
    }

    public Optional<Cat> getCatById(Long catId) {
        return catRepository.findById(catId);
    }

    public List<Cat> getCatsByUserId(Long userId) {
        return catRepository.findByUserId(userId);
    }
}
