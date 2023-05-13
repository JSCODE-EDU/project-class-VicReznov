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
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody final ArticleRequestDto articleDto) {
        ArticleResponseDto savedArticleDto;
        try {
            savedArticleDto = articleService.createArticle(articleDto);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
        }

        return new ResponseEntity<>(savedArticleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getLimitedSortedArticlesOfKeyword(
                                                        @RequestParam(required = false) String keyword,
                                                        @RequestParam(defaultValue = "100") int limit) {
        List<ArticleResponseDto> result;

        try {
            result = articleService.searchArticlesByKeyword(keyword, limit);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(new ArticleResponseDto(e.getMessage())));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable long articleId) {
        ArticleResponseDto result;
        try {
            result = articleService.getArticle(articleId);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
        }

    }

    @DeleteMapping(value = "/{articleId}")
    public ResponseEntity<ArticleResponseDto> deleteArticle(@PathVariable long articleId) {

        try {
            articleService.deleteArticle(articleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
        }
    }
}
