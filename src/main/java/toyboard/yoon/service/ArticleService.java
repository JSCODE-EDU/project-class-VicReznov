package toyboard.yoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toyboard.yoon.domain.Article;
import toyboard.yoon.dto.ArticleDto;
import toyboard.yoon.mapper.ArticleMapper;
import toyboard.yoon.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = ArticleMapper.dtoToArticle(articleDto);

        this.articleRepository.save(article);

        return ArticleMapper.articleToDto(article);
    }

    public List<ArticleDto> getArticles() {
        List<Article> articles = articleRepository.findAll();

        List<ArticleDto> articleDtos = new ArrayList<>();
        for(Article article : articles) {
            ArticleDto articleDto = ArticleMapper.articleToDto(article);

            articleDtos.add(articleDto);
        }

        return articleDtos;
    }

    public ArticleDto getArticle(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        ArticleDto articleDto;

        if(article.isEmpty()) {
            articleDto = new ArticleDto();
        } else {
            articleDto = ArticleMapper.articleToDto(article.get());
        }

        return articleDto;
    }

    public ArticleDto updateArticle(Long articleId, ArticleDto articleDto) {
        Optional<Article> article = articleRepository.findById(articleId);

        if(article.isEmpty()) {
            throw new NoSuchElementException(String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
        }

        Article updateArticle = article.get();
        updateArticle.setTitle(articleDto.getTitle());
        updateArticle.setContents(articleDto.getContents());
        updateArticle.setAuthor(articleDto.getAuthor());

        articleRepository.save(updateArticle);

        return ArticleMapper.articleToDto(updateArticle);
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
