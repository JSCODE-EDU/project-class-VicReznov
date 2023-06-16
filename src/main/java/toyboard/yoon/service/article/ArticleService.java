package toyboard.yoon.service.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.entity.member.Member;
import toyboard.yoon.exception.RestApiException;
import toyboard.yoon.entity.Article;
import toyboard.yoon.dto.article.ArticleRequestDto;
import toyboard.yoon.dto.article.ArticleResponseDto;
import toyboard.yoon.exhandler.advice.GlobalErrorCode;
import toyboard.yoon.repository.ArticleRepository;
import toyboard.yoon.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    final int MIN_KEYWORD_LENGTH = 1;

    final String KEYWORD_LENGTH_ERROR = String.format("검색하려는 단어의 길이는 %d 이상이어야 합니다.", MIN_KEYWORD_LENGTH);

    private final ArticleRepository articleRepository;
    private MemberRepository memberRepository;

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto articleDto) {
        Article article = Article.builder()
                .articleId(articleDto.getArticleId())
                .title(articleDto.getTitle())
                .contents(articleDto.getContents())
                .build();

        Member member = memberRepository.findById(articleDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        article.setMember(member);

        Article savedArticle = articleRepository.save(article);

        return ArticleResponseDto.builder()
                .articleId(savedArticle.getArticleId())
                .title(savedArticle.getTitle())
                .contents(savedArticle.getContents())
                .createdAt(savedArticle.getCreatedAt())
                .build();
    }

    public ArticleResponseDto getArticle(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);

        if(article.isEmpty()) {
//            throw new EntityNotFoundException(String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
//            throw new RestApiException(GlobalErrorCode.NOT_FOUND, String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
            throw new RestApiException(GlobalErrorCode.NOT_FOUND);
        }

        return ArticleResponseDto.builder()
                .articleId(article.get().getArticleId())
                .title(article.get().getTitle())
                .contents(article.get().getContents())
                .createdAt(article.get().getCreatedAt())
                .build();
    }

    public List<ArticleResponseDto> searchArticlesByKeyword(String keyword, int limit) {

        verifyKeyword(keyword);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0, limit, sort);

        List<Article> articles = articleRepository.findByTitleContaining(keyword, pageable);

        return null;
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, ArticleRequestDto articleDto) {
        Optional<Article> article = articleRepository.findById(articleId);

        if(article.isEmpty()) {
//            throw new EntityNotFoundException(String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
//            throw new RestApiException(GlobalErrorCode.NOT_FOUND, String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
            throw new RestApiException(GlobalErrorCode.NOT_FOUND);
        }

        Article updateArticle = article.get();
        updateArticle.setTitle(articleDto.getTitle());
        updateArticle.setContents(articleDto.getContents());

        articleRepository.save(updateArticle);

        return ArticleResponseDto.builder()
                .articleId(article.get().getArticleId())
                .title(article.get().getTitle())
                .contents(article.get().getContents())
                .createdAt(article.get().getCreatedAt())
                .build();
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);

        if(article.isEmpty()) {
//            throw new EntityNotFoundException(String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
//            throw new RestApiException(GlobalErrorCode.NOT_FOUND, String.format("Article Id '%d'가 존재하지 않습니다.", articleId));
            throw new RestApiException(GlobalErrorCode.NOT_FOUND);
        }

        articleRepository.deleteById(articleId);
    }

    private void verifyKeyword(String keyword) {
        verifyKeyWordLength(keyword);
        verifyKBlank(keyword);
    }

    private void verifyKeyWordLength(String keyword) {
        if(keyword.length() < MIN_KEYWORD_LENGTH) {
//            throw new IllegalArgumentException(KEYWORD_LENGTH_ERROR);
            throw new RestApiException(GlobalErrorCode.METHOD_UNPROCESSABLE_ENTITY);
        }
    }

    private void verifyKBlank(String keyword) {
        if(keyword.trim().length() == 0) {
//            throw new IllegalArgumentException(KEYWORD_LENGTH_ERROR);
            throw new RestApiException(GlobalErrorCode.METHOD_UNPROCESSABLE_ENTITY);
        }
    }
}
