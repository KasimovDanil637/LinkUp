package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.exception.DataException;
import web.model.Stats;
import web.model.User;
import web.repositories.StatsRepository;

import java.sql.Date;

@Service
public class StatsService {
    @Autowired
    private StatsRepository repository;
    @Autowired
    private UserService userService;
    public Stats findByUser(User user) {
        return repository.findByUser(user).orElseThrow(() -> new DataException(
                "Stats not found"));
    }

    public void save(String username) {
        User user = userService.findUserByUsername(username);
        Stats stats = new Stats();
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        stats.setDate(currentDate);
        stats.setUser(user);
        repository.save(stats);}

    public int count(User user, Date date) { return repository.countByUser(user, date);}

    public Date minDate(User user){ return  repository.minDateByUser(user);}
}
