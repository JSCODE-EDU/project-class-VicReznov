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
}
