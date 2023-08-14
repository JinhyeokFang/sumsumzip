package uk.jinhy.sumsumzip.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Table(name = "users")
@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Column(unique = true)
    private String email;

    @Column
    private String name;

    @Column
    private String picture;

    @OneToMany(targetEntity = CatLikes.class, mappedBy = "user")
    private List<CatLikes> likeCats;

    @JsonIgnore
    @OneToMany(targetEntity = UserFollows.class, mappedBy = "follower")
    private List<User> following;

    @JsonIgnore
    @OneToMany(targetEntity = UserFollows.class, mappedBy = "following")
    private List<User> followers;

    @Builder
    public User(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public User updateUser(String name) {
        this.name = name;
        return this;
    }
}
