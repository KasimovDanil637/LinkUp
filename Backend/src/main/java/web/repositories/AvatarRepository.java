package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Avatar;
import web.model.User;
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {


    Avatar getAvatarByUser(User user);

}
