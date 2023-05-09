package toyboard.yoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyboard.yoon.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
