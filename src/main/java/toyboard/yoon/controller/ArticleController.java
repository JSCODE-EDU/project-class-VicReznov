package toyboard.yoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyboard.yoon.dto.ArticleDto;
import toyboard.yoon.service.ArticleService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody final ArticleDto articleDto) {
        ArticleDto savedArticleDto = articleService.createArticle(articleDto);
        return new ResponseEntity<>(savedArticleDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getLimitedSortedArticles(@RequestParam(defaultValue = "100") int limit) {
        List<ArticleDto> result = articleService.getLimitedArticlesSortedByCreatedAtDesc(limit);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{articleId}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable long articleId) {
        ArticleDto result = articleService.getArticle(articleId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/{articleId}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long articleId, @RequestBody ArticleDto articleDto) {
        ArticleDto result = articleService.updateArticle(articleId, articleDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long articleId) {
        articleService.deleteArticle(articleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
