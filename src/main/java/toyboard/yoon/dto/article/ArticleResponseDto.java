package toyboard.yoon.dto.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ArticleResponseDto {

    private Long articleId;
    private String title;
    private String contents;
    private Date createdAt;

    private String message;

    @Builder
    public ArticleResponseDto(Long articleId, String title, String contents, Date createdAt) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public ArticleResponseDto(String message) {
        this.message = message;
    }
}
