package com.cts.newsnest.wishlist.repository;

import com.cts.newsnest.wishlist.entity.Article;
import com.cts.newsnest.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

//    @Query("select Ar")
//    public Optional<List<Article>> findArticlesByEmail(String email);
    public Optional<WishList> findByEmail(String email);

    @Modifying
    @Query("DELETE FROM WishList w WHERE w.email = :email")
    public void deleteByEmail(@Param("email") String email);
}
