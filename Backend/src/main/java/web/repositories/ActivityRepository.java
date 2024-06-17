package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.ActivityPost;
import web.model.Post;
import web.model.User;
import web.model.UserInfo;

import java.util.List;

@Repository
public interface ActivityRepository  extends JpaRepository<ActivityPost, Long> {

    List<ActivityPost> findByPost(Post post);

    ActivityPost findByPostAndUser(Post post, User user);

    @Query("select count(*) from ActivityPost a where a.post = :post and a.isAction = true")
    int getLikesByPost(Post post);

    @Query("SELECT COUNT(p.id) FROM Post p JOIN ActivityPost ap ON p.id = ap.post.id " +
            "WHERE p.user = :user AND p.isDeleted = false AND ap.isAction = true")
    int getCountPostByUser(User user);


}
