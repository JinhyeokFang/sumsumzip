package uk.jinhy.sumsumzip.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name = "cats")
@Entity
@NoArgsConstructor
@Getter
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column
    private String url;

    @Column
    private Long userId;

    @Column
    private String title;

    @Column
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public Cat(String url, Long userId, String title, String description) {
        this.url = url;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }
}
