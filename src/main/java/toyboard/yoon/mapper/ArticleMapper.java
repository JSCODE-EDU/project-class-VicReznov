package toyboard.yoon.mapper;

import toyboard.yoon.domain.Article;
import toyboard.yoon.dto.ArticleDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleMapper {

    public static ArticleDto articleToDto(Article article) {
        ArticleDto articleDto = ArticleDto.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .contents(article.getContents())
                .author(article.getTitle())
                .createdAt(article.getCreatedAt())
                .build();

        return articleDto;
    }

    public static List<ArticleDto> articleToDtos(List<Article> articles) {
        List<ArticleDto> articleDtos = new ArrayList<>();

        for(Article article : articles) {
            ArticleDto articleDto = articleToDto(article);
            articleDtos.add(articleDto);
        }

        return articleDtos;
    }

    public static Article dtoToArticle(ArticleDto articleDto) {
        Article article = new Article();

        article.setTitle(articleDto.getTitle());
        article.setContents(articleDto.getContents());
        article.setAuthor(articleDto.getAuthor());
        article.setPassword(articleDto.getPassword());
        article.setCreatedAt(articleDto.getCreatedAt());

        return article;
    }

}
