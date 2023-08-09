package uk.jinhy.sumsumzip.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.jinhy.sumsumzip.entity.Cat;
import uk.jinhy.sumsumzip.service.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CatController {
    private final S3Service s3Service;
    private final UserService userService;
    private final CatService catService;

    private final Logger logger = LoggerFactory.getLogger(CatController.class);

    @PostMapping("/upload")
    public String uploadCatImage(
            @RequestPart(value = "image") MultipartFile imageFile
    ) {
        try {
            var userId = userService.getUserIdByEmail("jinhyeokfang@gmail.com");
            var imageURL = s3Service.saveFile(imageFile);
            catService.addCat(imageURL, userId);
            return imageURL;
        } catch (Exception error) {
            logger.error(error.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/{catId}")
    public void removeCatImage(
            @PathVariable Long catId
    ) {
        catService.deleteCat(catId);
    }

    @GetMapping("/")
    public List<Cat> getCats() {
        return catService.getCats();
    }
}
