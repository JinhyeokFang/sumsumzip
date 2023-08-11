package uk.jinhy.sumsumzip.controller.cat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UploadCatImageDTO {
    private boolean success;
    private String imageURL;
}
