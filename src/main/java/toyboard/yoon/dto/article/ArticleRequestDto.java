package toyboard.yoon.dto.article;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
public class ArticleRequestDto {

    private Long articleId;

    @NotEmpty(message = "제목을 입력해야합니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해야합니다.")
    private String contents;

    private Long memberId;

    @Builder
    public ArticleRequestDto(Long articleId, String title, String contents, Long memberId) {
        this.articleId = articleId;
        this.title = title;
        this.contents = contents;
        this.memberId = memberId;
    }
}
