package toyboard.yoon.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyboard.yoon.comment.domain.Comment;
import toyboard.yoon.entity.Article;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT distinct c from Article a left join fetch a.member where a.post = :post")
    List<Comment> findCommentsByArticle(Article article);
}
