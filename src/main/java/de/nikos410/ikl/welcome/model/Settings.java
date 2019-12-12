package de.nikos410.ikl.welcome.model;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Settings {
    @Id
    @GeneratedValue
    private long id;

    private int imageDisplayDuration = 8;
    private int newsDisplayDuration = 24;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public int getImageDisplayDuration() {
        return imageDisplayDuration;
    }

    public void setImageDisplayDuration(int imageDisplayDuration) {
        this.imageDisplayDuration = imageDisplayDuration;
    }

    @Column(nullable = false)
    public int getNewsDisplayDuration() {
        return newsDisplayDuration;
    }

    public void setNewsDisplayDuration(int showNewsDuration) {
        this.newsDisplayDuration = showNewsDuration;
    }
}
