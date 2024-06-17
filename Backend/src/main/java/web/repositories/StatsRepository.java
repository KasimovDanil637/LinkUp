package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.Stats;
import web.model.User;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long> {

    Optional<Stats> findByUser(User user);
    @Query("select count(s) from Stats s where s.user = :user and s.date = :date")
    int countByUser(User user, Date date);

    @Query("select s.date from Stats s where s.user = :user order by s.date asc limit 1")
    Date minDateByUser(User user);
}
