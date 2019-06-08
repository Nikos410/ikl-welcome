package de.nikos410.ikl.welcome.service;

import de.nikos410.ikl.welcome.model.NewsArticle;

import java.util.List;

public interface NewsService {
    NewsArticle getNext(Long lastId);

    List<NewsArticle> getAll();

    void editArticle(Long id, NewsArticle editedArticle);
}
