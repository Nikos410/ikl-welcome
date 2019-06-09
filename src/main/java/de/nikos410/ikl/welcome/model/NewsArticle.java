package de.nikos410.ikl.welcome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NewsArticle {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String headline;
    private String introduction;
    @Column(length = 2048)
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Column(length = 2048)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
