package toyboard.yoon.mapper;

import toyboard.yoon.domain.Article;
import toyboard.yoon.dto.ArticleDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleMapper {

    public static ArticleDto articleToDto(Article article) {
        ArticleDto articleDto = new ArticleDto();

        articleDto.setArticleId(article.getArticleId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContents(article.getContents());
        articleDto.setAuthor(article.getAuthor());
//        articleDto.setPassword(article.getPassword());
        articleDto.setCreatedAt(article.getCreatedAt());

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
