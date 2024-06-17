package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.Friends;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
  @Query("select f from Friends f where (f.friendTwo = :user or f.friendOne = :user) and  f.isDeleted = false")
  List<Friends> findUserFriendsNotDeleted(User user);
  @Query("select f from Friends f where f.friendTwo != :user and f.friendOne != :user and (f.friendTwo = :friend or f.friendOne != :friend) ")
  List<Friends> findFriendsNotUser(User user, User friend);
  @Query("select f from Friends f where" +
          " (f.friendTwo = :user and f.friendOne = :friend) " +
          "or " +
          "(f.friendTwo = :friend and f.friendOne = :user) ")
  Friends getFriendsByTwoUsers(User user, User friend);
}
