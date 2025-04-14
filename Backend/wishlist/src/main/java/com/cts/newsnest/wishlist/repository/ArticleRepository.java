package com.cts.newsnest.wishlist.repository;

import com.cts.newsnest.wishlist.entity.Article;
import com.cts.newsnest.wishlist.entity.WishList;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    //    @Query("select Ar")
//    public Optional<List<Article>> findArticlesByEmail(String email);

    public Optional<Article> findByArticleId(int articleId);

    @Query("select  a from Article a where a.user.userId = :userId")
    public List<Article> findByUserId(@Param("userId") int userId);

    @Query("select a from Article a where a.url = :getUrl and a.user.userId = :getUserId")
    public Optional<Article> findByUrlAndUserId(@Param("getUrl") String getUrl, @Param("getUserId") int getUserId);

    @Modifying
    @Query("DELETE from Article a where a.user.userId = :userId")
    public void deleteAllByUserId(@Param("userId") int userId);

    @Modifying
    @Query("DELETE from Article a where a.articleId = :articleId")
    public void deleteArticleByArticleId(int articleId);
}