package uk.jinhy.sumsumzip.controller.cat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddCommentRequestDTO {
    private Long catId;
    private String content;
}
