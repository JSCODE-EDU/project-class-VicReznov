package toyboard.yoon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class ArticleDto {

    private Long articleId;
    private String title;
    private String contents;
    private String author;
    private String password;
    private Date createdAt;

    @Builder
    public ArticleDto(Long articleId, String title, String contents, String author, String password, Date createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
    }
}
