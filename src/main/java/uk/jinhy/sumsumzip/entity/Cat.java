package uk.jinhy.sumsumzip.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "cats")
@Entity
@NoArgsConstructor
@Getter
public class Cat {
    @Id
    @Column(name = "catId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String url;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(targetEntity = CatLikes.class, mappedBy = "cat")
    private List<CatLikes> likeUsers;

    @Builder
    public Cat(User user, String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.user = user;
        this.description = description;
    }
}
