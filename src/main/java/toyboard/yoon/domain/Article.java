package toyboard.yoon.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article", schema="toy_board", uniqueConstraints = {@UniqueConstraint(columnNames = "article_id")})
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id", unique = true, nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(unique = true, nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
}
