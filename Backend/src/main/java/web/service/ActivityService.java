package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.ActivityPost;
import web.model.Post;
import web.model.User;
import web.repositories.ActivityRepository;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void save(Post post, User user){
        ActivityPost activityPost = findByPostAndUser(post, user);
        if (activityPost != null) {
            activityPost.setAction(!activityPost.isAction());
        } else {
            activityPost = new ActivityPost();
            activityPost.setPost(post);
            activityPost.setUser(user);
            activityPost.setAction(true);
        }
        activityRepository.save(activityPost);
    }
    public List<ActivityPost> findByPost(Post post) { return activityRepository.findByPost(post);}
    public int getLikesByPost(Post post) { return activityRepository.getLikesByPost(post);}
    public int getCountPostByUser(User user) { return activityRepository.getCountPostByUser(user);}
    public ActivityPost findByPostAndUser(Post post, User user) { return activityRepository.findByPostAndUser(post, user);}
}
