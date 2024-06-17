package web.service;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dto.request.ValueDto;
import web.exception.DataException;
import web.model.Post;
import web.model.RoleType;
import web.model.User;
import web.repositories.PostRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AvatarService avatarService;
    public void savePost(String username, ValueDto valueDto) {
        Post post = new Post();
        post.setUser(userService.findUserByUsername(username));
        post.setMessage(valueDto.getValue());
        post.setDeleted(false);
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        post.setTime(timestamp);
        postRepository.save(post);
    }
    public void updatePost(String username, Part file) throws IOException {
        Post post = findLastPostByUser(userService.findUserByUsername(username));
        byte[] array = avatarService.uploadFile(file);
        post.setPhoto(array);
        postRepository.save(post);
    }
    public void deletePost(String username, long id){
        User user = userService.findUserByUsername(username);
        Post post = findPostById(id);
        if (user.getUserRole().equals(RoleType.USER) && post.getUser().getUsername().equals(user.getUsername())) {
            post.setDeleted(true);
        } else if (user.getUserRole().equals(RoleType.ADMIN)) {
            post.setDeleted(true);
        }
        postRepository.save(post);
    }
    public List<Post> findPostByUser(User user) { return  postRepository.findPostByUserOrderByIdDesc(user);}
    public List<Post> findAllPost() { return  postRepository.findAllPost();}
    public Post findPostById(Long id) { return  postRepository.findPostById(id).orElseThrow(() -> new DataException(
            "Post not found"));}
    public Post findLastPostByUser(User user) { return  postRepository.findLastPostByUser(user);}

    public int countAllByUser(User user){ return postRepository.countAllByUser(user);}

}
