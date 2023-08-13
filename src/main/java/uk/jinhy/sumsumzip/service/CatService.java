package uk.jinhy.sumsumzip.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.entity.Cat;
import uk.jinhy.sumsumzip.repository.CatRepository;
import uk.jinhy.sumsumzip.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CatService {
    private final UserRepository userRepository;
    private final CatRepository catRepository;
    public void addCat(String url, Long userId, String title, String description) {
        var user = userRepository.findById(userId).get();
        var cat = Cat.builder()
                .url(url)
                .user(user)
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

    public List<Cat> getCats(Long pageNumber) {
        var page = PageRequest.of(pageNumber.intValue(), 10, Sort.by(Sort.Direction.DESC, "id"));
        return catRepository.findAll(page).getContent();
    }
    public List<Cat> getCats() {
        return getCats(0l);
    }

    public Optional<Cat> getCatById(Long catId) {
        return catRepository.findById(catId);
    }

    public List<Cat> getCatsByUserId(Long userId) {
        return catRepository.findByUserId(userId);
    }
}
