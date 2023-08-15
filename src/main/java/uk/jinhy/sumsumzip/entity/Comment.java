package uk.jinhy.sumsumzip.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comments")
@Entity
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @Column(name = "commentsId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Column
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Cat cat;

    @Builder
    public Comment(String content, User user, Cat cat) {
        this.content = content;
        this.user = user;
        this.cat = cat;
    }

    public void editContent(String newContent) {
        this.content = newContent;
    }
}
