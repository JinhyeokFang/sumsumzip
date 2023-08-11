package uk.jinhy.sumsumzip.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import uk.jinhy.sumsumzip.controller.cat.GetCatDTO;
import uk.jinhy.sumsumzip.controller.cat.UploadCatImageDTO;
import uk.jinhy.sumsumzip.service.*;

@RequiredArgsConstructor
@RequestMapping("/cat")
@RestController
public class CatController {
    private final S3Service s3Service;
    private final UserService userService;
    private final CatService catService;

    private final Logger logger = LoggerFactory.getLogger(CatController.class);

    @PostMapping("/upload")
    public UploadCatImageDTO uploadCatImage(
            @RequestPart(value = "image") MultipartFile imageFile,
            @RequestPart(value = "title") String title,
            @RequestPart(value = "description") String description,
            Authentication authentication
    ) {
        if (authentication == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "토큰이 필요합니다."
            );
        }
        try {
            var email = (String) authentication.getPrincipal();
            logger.info(email);
            var userId = userService.getUserIdByEmail(email);
            var imageURL = s3Service.saveFile(imageFile);
            catService.addCat(imageURL, userId, title, description);
            return new UploadCatImageDTO(
                    true,
                    imageURL
            );
        } catch (Exception error) {
            logger.error(error.getMessage());
            return new UploadCatImageDTO(
                    false,
                    ""
            );
        }
    }

    @DeleteMapping("/{catId}")
    public void removeCatImage(@PathVariable Long catId) {
        catService.deleteCat(catId);
    }

    @GetMapping("/")
    public GetCatDTO getCats() {
        return new GetCatDTO(
                catService.getCats()
        );
    }

    @GetMapping("/user/{userId}")
    public GetCatDTO getCatsByUserId(
            @PathVariable Long userId
    ) {
        return new GetCatDTO(
                catService.getCatsByUserId(userId)
        );
    }
}
