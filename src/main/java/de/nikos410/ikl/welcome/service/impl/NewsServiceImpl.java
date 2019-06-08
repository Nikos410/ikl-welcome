package de.nikos410.ikl.welcome.service.impl;

import de.nikos410.ikl.welcome.database.NewsArticleRepository;
import de.nikos410.ikl.welcome.model.NewsArticle;
import de.nikos410.ikl.welcome.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private static final NewsArticle FALLBACK_ARTICLE = new NewsArticle();
    static {
        FALLBACK_ARTICLE.setHeadline("Willkommen im IKL Vereinsheim!");
        FALLBACK_ARTICLE.setIntroduction("Machen sie es sich bequem und sehen sie sich um!");
    }

    private final NewsArticleRepository newsArticleRepository;

    public NewsServiceImpl(NewsArticleRepository newsArticleRepository) {
        this.newsArticleRepository = newsArticleRepository;
    }

    @Override
    public NewsArticle getNext(Long lastId) {
        final List<NewsArticle> allArticles = newsArticleRepository.findAll();

        // Return fallback article if no articles can be found
        if (allArticles.isEmpty()) {
            return FALLBACK_ARTICLE;
        }

        // Return the article with the next highest ID
        for (NewsArticle article : allArticles) {
            if (article.getId() > lastId) {
                return article;
            }
        }

        // If no artice with a higher ID can be found, return the first article
        return allArticles.get(0);
    }

    @Override
    public List<NewsArticle> getAll() {
        return null;
    }
}