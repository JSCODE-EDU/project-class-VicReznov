package toyboard.yoon.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.entity.Article;
import toyboard.yoon.entity.member.Member;
import toyboard.yoon.like.dto.ArticleLikeResponse;
import toyboard.yoon.like.entity.ArticleLike;
import toyboard.yoon.like.repository.ArticleLikeRepository;
import toyboard.yoon.repository.ArticleRepository;
import toyboard.yoon.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public ArticleLikeResponse flipPostLike(final Member member, final Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(EntityNotFoundException::new);
        if (articleLikeRepository.existsByArticleAndMemberId(article, member.getId())) {
            articleLikeRepository.deleteByArticleAndMemberId(article, member.getId());
            return new ArticleLikeResponse(false);
        }
        addPostLike(article, member);

        return new ArticleLikeResponse(true);
    }

    private void addPostLike(final Article article, final Member member) {

        ArticleLike postLike = ArticleLike.builder()
                .member(member)
                .article(article)
                .build();

        articleLikeRepository.save(postLike);
    }
}
