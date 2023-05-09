package toyboard.yoon.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Setter
@Getter
public class ArticleDto {

    private Long articleId;
    private String title;
    private String contents;
    private String author;
    private String password;
    private Date createdAt;
}
