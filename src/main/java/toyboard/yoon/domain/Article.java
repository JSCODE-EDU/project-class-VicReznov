package toyboard.yoon.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article", schema="toy_board", uniqueConstraints = {@UniqueConstraint(columnNames = "article_id")})
@Getter
public class Article {

    final int MIN_TITLE_LENGTH = 1;
    final int MAX_TITLE_LENGTH = 15;
    final int MIN_CONTENTS_LENGTH = 1;
    final int MAX_CONTENTS_LENGTH = 1000;

    final String TITLE_LENGTH_ERROR = String.format("제목의 길이는 %d ~ %d 이어야 합니다.", MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);
    final String CONTENTS_LENGTH_ERROR = String.format("내용의 길이는 %d ~ %d 이어야 합니다.", MIN_CONTENTS_LENGTH, MAX_CONTENTS_LENGTH);

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

    public void setTitle(String title) {
        verifyTitle(title);
        this.title = title;
    }

    public void setContents(String contents) {
        verifyContents(contents);
        this.contents = contents;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private void verifyTitle(String title) {
        verifyTitleLength(title);
        verifyBlank(title);
    }

    private void verifyTitleLength(String title) {
        if(title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(TITLE_LENGTH_ERROR);
        }
    }

    private void verifyBlank(String input) {
        if(input.trim().length() == 0) {
            throw new IllegalArgumentException(TITLE_LENGTH_ERROR);
        }
    }

    private void verifyContents(String contents) {
        verifyContentsLength(contents);
    }

    private void verifyContentsLength(String contents) {
        if(contents.length() < MIN_CONTENTS_LENGTH || contents.length() > MAX_CONTENTS_LENGTH) {
            throw new IllegalArgumentException(CONTENTS_LENGTH_ERROR);
        }
    }
}
