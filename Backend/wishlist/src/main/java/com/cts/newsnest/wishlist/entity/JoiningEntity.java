package com.cts.newsnest.wishlist.entity;

import jakarta.persistence.*;

@Entity
public class JoiningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int joiningId;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private WishList wishList;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public JoiningEntity(){}

    public JoiningEntity(int joiningId, WishList wishList, Article article) {
        this.joiningId = joiningId;
        this.wishList = wishList;
        this.article = article;
    }

    public JoiningEntity(WishList wishList, Article article) {
        this.wishList = wishList;
        this.article = article;
    }

    public int getJoiningId() {
        return joiningId;
    }

    public void setJoiningId(int joiningId) {
        this.joiningId = joiningId;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
