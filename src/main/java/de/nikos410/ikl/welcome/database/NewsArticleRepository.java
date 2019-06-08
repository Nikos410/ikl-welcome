package de.nikos410.ikl.welcome.database;

import de.nikos410.ikl.welcome.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    NewsArticle findOneById(Long id);
}
