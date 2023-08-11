package uk.jinhy.sumsumzip.controller.cat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uk.jinhy.sumsumzip.entity.Cat;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCatDTO {
    private List<Cat> cats;
}
