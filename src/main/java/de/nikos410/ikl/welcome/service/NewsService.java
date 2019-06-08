package de.nikos410.ikl.welcome.service;

import de.nikos410.ikl.welcome.model.NewsArticle;

import java.util.List;

public interface NewsService {
    NewsArticle nextArticle(Long lastId);

    List<NewsArticle> getAll();

    void addArticle(NewsArticle newArticle);

    void editArticle(Long id, NewsArticle editedArticle);

    void deleteArticle(Long id);
}
