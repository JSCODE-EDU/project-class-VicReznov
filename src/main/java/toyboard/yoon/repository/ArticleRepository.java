package toyboard.yoon.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toyboard.yoon.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

//    List<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Article> findByTitleContaining(String keyword, Pageable pageable);
}
