package toyboard.yoon.mapper;

import toyboard.yoon.domain.Article;
import toyboard.yoon.dto.article.ArticleRequestDto;
import toyboard.yoon.dto.article.ArticleResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ArticleMapper {

    public static ArticleResponseDto articleToDto(Article article) {
        ArticleResponseDto articleDto = ArticleResponseDto.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .contents(article.getContents())
                .author(article.getTitle())
                .createdAt(article.getCreatedAt())
                .build();

        return articleDto;
    }

    public static List<ArticleResponseDto> articleToDtos(List<Article> articles) {
        List<ArticleResponseDto> articleDtos = new ArrayList<>();

        for(Article article : articles) {
            ArticleResponseDto articleDto = articleToDto(article);
            articleDtos.add(articleDto);
        }

        return articleDtos;
    }

    public static Article dtoToArticle(ArticleRequestDto articleDto) {
        Article article = new Article();

        article.setTitle(articleDto.getTitle());
        article.setContents(articleDto.getContents());
        article.setAuthor(articleDto.getAuthor());
        article.setPassword(articleDto.getPassword());
        article.setCreatedAt(articleDto.getCreatedAt());

        return article;
    }

}
