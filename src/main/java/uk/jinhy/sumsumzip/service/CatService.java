package uk.jinhy.sumsumzip.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.entity.Cat;
import uk.jinhy.sumsumzip.entity.CatLikes;
import uk.jinhy.sumsumzip.repository.CatRepository;
import uk.jinhy.sumsumzip.repository.CatLikesRepository;
import uk.jinhy.sumsumzip.repository.UserRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CatService {
    private final UserRepository userRepository;
    private final CatRepository catRepository;
    private final CatLikesRepository catLikesRepository;

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

    public List<Cat> getCats(Long pageNumber) {
        var page = PageRequest.of(pageNumber.intValue(), 10, Sort.by(Sort.Direction.DESC, "id"));
        return catRepository.findAll(page).getContent();
    }
    public List<Cat> getCats() {
        return getCats(0l);
    }

    public List<Cat> getCatsByUserId(Long userId) {
        return catRepository.findByUserId(userId);
    }

    public void like(String userEmail, Long catId) {
        var user = userRepository.findByEmail(userEmail).get();
        var cat = catRepository.findById(catId).get();
        var newLike = CatLikes.builder()
                .user(user)
                .cat(cat)
                .build();
        if (catLikesRepository.findByUserAndCat(user, cat).isEmpty()) {
            catLikesRepository.save(newLike);
        }
    }

    public void dislike(String userEmail, Long catId) {
        var user = userRepository.findByEmail(userEmail).get();
        var cat = catRepository.findById(catId).get();
        catLikesRepository.deleteAllByUserAndCat(user, cat);
    }
}
