package toyboard.yoon.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyboard.yoon.entity.Article;
import toyboard.yoon.like.entity.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    boolean existsByArticleAndMemberId(Article article, Long memberId);

    void deleteByArticleAndMemberId(Article article, Long memberId);
}
