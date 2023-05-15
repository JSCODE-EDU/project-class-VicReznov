package toyboard.yoon.dto.article;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleRequestDto {

    private Long articleId;
    private String title;
    private String contents;
    private String author;
    private String password;
    private Date createdAt;

    @Builder
    public ArticleRequestDto(Long articleId, String title, String contents, String author, String password, Date createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
    }
}
