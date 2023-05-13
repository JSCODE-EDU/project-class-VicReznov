package toyboard.yoon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyboard.yoon.dto.article.ArticleRequestDto;
import toyboard.yoon.dto.article.ArticleResponseDto;
import toyboard.yoon.service.ArticleService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody final ArticleRequestDto articleDto) {
        ArticleResponseDto savedArticleDto = articleService.createArticle(articleDto);
        return new ResponseEntity<>(savedArticleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getLimitedSortedArticlesOfKeyword(
                                                        @RequestParam(defaultValue = "") String keyword,
                                                        @RequestParam(defaultValue = "100") int limit) {
        List<ArticleResponseDto> result = articleService.searchArticlesByKeyword(keyword, limit);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable long articleId) {
        ArticleResponseDto result;
        try {
            result = articleService.getArticle(articleId);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping(value = "/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long articleId,
                                                            @RequestBody ArticleRequestDto articleDto) {

        try {
            ArticleResponseDto result = articleService.updateArticle(articleId, articleDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long articleId) {
        articleService.deleteArticle(articleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
