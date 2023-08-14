package uk.jinhy.sumsumzip.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cats_likes")
@Entity
@NoArgsConstructor
@Getter
public class CatLikes {
    @Id
    @Column(name = "catLikeId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "catId")
    private Cat cat;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public CatLikes(Cat cat, User user) {
        this.cat = cat;
        this.user = user;
    }
}
