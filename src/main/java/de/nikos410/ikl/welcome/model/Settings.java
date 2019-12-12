package de.nikos410.ikl.welcome.model;

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

    private BigDecimal imageDisplayDuration;
    private BigDecimal newsDisplayDuration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public BigDecimal getImageDisplayDuration() {
        return imageDisplayDuration;
    }

    public void setImageDisplayDuration(BigDecimal imageDisplayDuration) {
        this.imageDisplayDuration = imageDisplayDuration;
    }

    @Column(nullable = false)
    public BigDecimal getNewsDisplayDuration() {
        return newsDisplayDuration;
    }

    public void setNewsDisplayDuration(BigDecimal showNewsDuration) {
        this.newsDisplayDuration = showNewsDuration;
    }
}
