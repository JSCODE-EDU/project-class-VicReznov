package toyboard.yoon.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyboard.yoon.domain.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

//    List<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Article> findByTitleContainingOrderByCreatedAtDesc(String keyword, Pageable pageable);
}
