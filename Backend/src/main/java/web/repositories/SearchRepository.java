package web.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;
import web.model.User;

import java.util.List;

@Component
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> searchUser(String username) {
        username = username.trim().replaceAll("\\s+", ":* & ") + ":*";
        System.out.println(username);
        Query queryAdr =
                entityManager.createNativeQuery(
                        "select * from users us where to_tsvector(us.username) @@ to_tsquery(:username) " +
                                "and us.user_role = 'USER' order by us.username limit 5" , User.class);
        queryAdr.setParameter("username", username);
        return (List<User>) queryAdr.getResultList();
    }
}
