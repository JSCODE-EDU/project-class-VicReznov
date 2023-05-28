package toyboard.yoon.dto.article;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Getter
@Setter
public class ArticleRequestDto {

    private Long articleId;

    @Max(15)
    @Min(1)
    private String title;

    @Max(1000)
    @Min(1)
    private String contents;

    private String author;

    private Date createdAt;

    @Builder
    public ArticleRequestDto(Long articleId, String title, String contents, String author, String password, Date createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.createdAt = createdAt;
    }
}
