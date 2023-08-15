package uk.jinhy.sumsumzip.controller.cat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.jinhy.sumsumzip.controller.user.UserDTO;
import uk.jinhy.sumsumzip.entity.Cat;

import java.util.List;

@NoArgsConstructor
@Getter
public class CatDTO {
    private Long id;

    private String url;

    private String title;

    private String description;

    private List<UserDTO> likeList;

    private UserDTO user;

    private List<CommentDTO> comments;

    public CatDTO(Cat cat) {
        this.id = cat.getId();
        this.url = cat.getUrl();
        this.title = cat.getTitle();
        this.description = cat.getDescription();
        this.likeList = cat.getLikeUsers().stream().map(
                like -> new UserDTO(like.getUser())
        ).toList();
        this.user = new UserDTO(cat.getUser());
        this.comments = cat.getComments().stream().map(CommentDTO::new).toList();
    }
}
