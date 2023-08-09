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

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User updateUser(String name) {
        this.name = name;
        return this;
    }
}
