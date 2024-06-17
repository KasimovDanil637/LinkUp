package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.Friends;
import web.model.Invites;
import web.model.User;
import web.model.UserInfo;

import java.util.List;

@Repository
public interface InvitesRepository extends JpaRepository<Invites, Long>  {
    @Query("select i from Invites i where i.accepted = false and i.user = :user")
    List<Invites> findAllByUserNotAccepted(User user);
    @Query("select i from Invites i where i.accepted = false and i.friend = :friend and i.user = :user ")
    Invites findByUserAndFriend(User user, User friend);

//    @Query("select i from Invites i where i.friend = :friend and i.user = :user ")
//    Invites findAllByUserAndFriend(User user, User friend);
}
