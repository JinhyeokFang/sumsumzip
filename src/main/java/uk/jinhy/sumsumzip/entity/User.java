package uk.jinhy.sumsumzip.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(unique = true)
    private String email;

    @Column
    private String name;

    @Column
    private String picture;

    @CreatedDate
    private LocalDateTime createdDate;

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
