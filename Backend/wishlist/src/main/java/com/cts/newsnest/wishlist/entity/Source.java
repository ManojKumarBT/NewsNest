package com.cts.newsnest.wishlist.entity;

import jakarta.persistence.*;

@Entity
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sourceId;
    private String id;
    private String name;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Article article;

    public Source(int sourceId, String id, String name, Article article) {
        this.sourceId = sourceId;
        this.id = id;
        this.name = name;
        this.article = article;
    }

    public Source(){}

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
