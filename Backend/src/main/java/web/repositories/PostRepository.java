package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.Post;
import web.model.User;
import web.model.UserInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.user = :user and p.isDeleted = false order by p.id desc ")
    List<Post> findPostByUserOrderByIdDesc(User user);
    Optional<Post> findPostById(Long id);

    @Query("select p from Post p where p.isDeleted = false order by p.id desc ")
    List<Post> findAllPost();

    @Query("select p from Post p where p.user = :user order by p.id desc limit 1")
    Post findLastPostByUser(User user);

    @Query("SELECT COUNT(p.id) FROM Post p WHERE p.user = :user AND p.isDeleted = false")
    int countAllByUser(User user);
}
