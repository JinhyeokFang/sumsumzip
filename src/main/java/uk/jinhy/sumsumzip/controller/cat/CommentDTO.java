package uk.jinhy.sumsumzip.controller.cat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.jinhy.sumsumzip.controller.user.UserDTO;
import uk.jinhy.sumsumzip.entity.Comment;

@NoArgsConstructor
@Getter
public class CommentDTO {
    private String content;
    private UserDTO user;
    private Long id;

    public CommentDTO(Comment comment) {
        this.content = comment.getContent();
        this.user = new UserDTO(comment.getUser());
        this.id = comment.getId();
    }
}
