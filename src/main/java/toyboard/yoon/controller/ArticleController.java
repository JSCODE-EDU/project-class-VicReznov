package toyboard.yoon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import toyboard.yoon.dto.article.ArticleRequestDto;
import toyboard.yoon.dto.article.ArticleResponseDto;
import toyboard.yoon.service.article.ArticleService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "게시글 생성 요청", description = "게시글이 생성됩니다.", tags = { "ArticleController" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArticleRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody @Valid final ArticleRequestDto articleDto) {
        ArticleResponseDto savedArticleDto;
        try {
            savedArticleDto = articleService.createArticle(articleDto);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
        }

        return new ResponseEntity<>(savedArticleDto, HttpStatus.OK);
    }

    @Operation(summary = "게시글 조회 요청", description = "특정 문자를 포함한 게시글이 최대 100개 조회됩니다.", tags = { "ArticleController" })
    @Parameters({
            @Parameter(name = "keyword", description = "조회할 게시물이 포함한 keyword"),
            @Parameter(name = "limit", description = "조회할 게시물의 최대 개수")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArticleRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
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

    @Operation(summary = "특정 게시글 조회 요청", description = "게시글 id를 통해 조회됩니다.", tags = { "ArticleController" })
    @Parameter(name = "articleId", description = "조회할 게시물의 id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArticleRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
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

    @Operation(summary = "게시글 수정 요청", description = "게시글을 수정됩니다.", tags = { "ArticleController" })
    @Parameter(name = "articleId", description = "수정할 게시글 id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArticleRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping(value = "/{articleId}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long articleId,
                                                            @RequestBody @Valid ArticleRequestDto articleDto) {

        try {
            ArticleResponseDto result = articleService.updateArticle(articleId, articleDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
        }

    }

    @Operation(summary = "게시글 삭제 요청", description = "게시글이 삭제됩니다.", tags = { "ArticleController" })
    @Parameter(name = "articleId", description = "삭제할 게시글 id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ArticleRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping(value = "/{articleId}")
    public ResponseEntity<ArticleResponseDto> deleteArticle(@PathVariable long articleId) {

        try {
            articleService.deleteArticle(articleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArticleResponseDto(e.getMessage()));
        }
    }
}
