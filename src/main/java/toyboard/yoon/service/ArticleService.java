package toyboard.yoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.domain.Article;
import toyboard.yoon.dto.article.ArticleRequestDto;
import toyboard.yoon.dto.article.ArticleResponseDto;
import toyboard.yoon.mapper.ArticleMapper;
import toyboard.yoon.repository.ArticleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto articleDto) {
        Article article = ArticleMapper.dtoToArticle(articleDto);

        this.articleRepository.save(article);

        return ArticleMapper.articleToDto(article);
    }

    public ArticleResponseDto getArticle(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);

        if(article.isEmpty()) {
            throw new EntityNotFoundException(String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
        }

        return ArticleMapper.articleToDto(article.get());
    }

    public List<ArticleResponseDto> searchArticlesByKeyword(String keyword, int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0, limit, sort);

        List<Article> articles = articleRepository.findByTitleContainingOrderByCreatedAtDesc(keyword, pageable);

        return ArticleMapper.articleToDtos(articles);
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleRequestDto articleDto) {
        Optional<Article> article = articleRepository.findById(articleId);

        if(article.isEmpty()) {
            throw new EntityNotFoundException(String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
        }

        Article updateArticle = article.get();
        updateArticle.setTitle(articleDto.getTitle());
        updateArticle.setContents(articleDto.getContents());
        updateArticle.setAuthor(articleDto.getAuthor());

        articleRepository.save(updateArticle);

        return ArticleMapper.articleToDto(updateArticle);
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
