package toyboard.yoon.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyboard.yoon.comment.domain.Comment;
import toyboard.yoon.entity.Article;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT distinct c from Comment c left join fetch c.member where c.article = :article")
    List<Comment> findCommentsByArticle(Article article);
}
