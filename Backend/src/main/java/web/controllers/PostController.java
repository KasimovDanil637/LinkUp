package web.controllers;

import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.dto.DtoService;
import web.dto.request.ActivityDto;
import web.dto.request.ValueDto;
import web.dto.response.PostActionDto;
import web.dto.response.ResponseMessage;
import web.model.*;
import web.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin("${cors.hosts}")
@PreAuthorize("isAuthenticated()")
public class PostController {
    private final Logger log = LoggerFactory.getLogger(FriendsController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private FriendService friendService;

    @RequestMapping(value = "/savephoto")
    public ResponseEntity<ResponseMessage> savePhoto(@RequestPart("file") Part file, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String username = userDetails.getUsername();
        postService.updatePost(username, file);
        return ResponseEntity.ok(DtoService.responseMessage("ok"));
    }

    @RequestMapping("/savemessage")
    public ResponseEntity<ResponseMessage> saveMessage(@RequestBody @Valid ValueDto valueDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String username = userDetails.getUsername();
        postService.savePost(username, valueDto);
        return ResponseEntity.ok(DtoService.responseMessage("ok"));
    }
    @GetMapping(value = "/list")
    public ResponseEntity<?> getList(@RequestParam("username") String username, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        log.debug(username);
        String mainUsername = userDetails.getUsername();
        User user = userService.findUserByUsername(username);
        User mainUser = userService.findUserByUsername(mainUsername);
        List<Post> posts = postService.findPostByUser(user);
        List<PostActionDto> postsActions = new ArrayList<>();
        for (Post post : posts){
            int likes = activityService.getLikesByPost(post);
            ActivityPost actionPost =  activityService.findByPostAndUser(post, mainUser);
            boolean action = actionPost != null && actionPost.isAction();
            Friends friends = friendService.getFriends(mainUser,post.getUser());
            boolean isFriend = friends != null && !friends.isDeleted();
            PostActionDto postActionDto = new PostActionDto(DtoService.postDto(post),
                    likes,
                    action,
                    DtoService.avatarDto(avatarService.getAvatarByUser(post.getUser())),
                    mainUsername.equals(username),
                    isFriend,
                    mainUser.getUserRole().equals(RoleType.ADMIN)
            );
            postsActions.add(postActionDto);
        }
        return ResponseEntity.ok(postsActions);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllList(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String mainUsername = userDetails.getUsername();
        User mainUser = userService.findUserByUsername(mainUsername);
        List<Post> posts = postService.findAllPost();
        List<PostActionDto> postsActions = new ArrayList<>();
        for (Post post : posts){
            int likes = activityService.getLikesByPost(post);
            ActivityPost actionPost =  activityService.findByPostAndUser(post, mainUser);
            boolean action = actionPost != null && actionPost.isAction();
            Friends friends = friendService.getFriends(mainUser,post.getUser());
            boolean isFriend = friends != null && !friends.isDeleted();
            PostActionDto postActionDto = new PostActionDto(
                    DtoService.postDto(post),
                    likes,
                    action,
                    DtoService.avatarDto(avatarService.getAvatarByUser(post.getUser())),
                    mainUsername.equals(post.getUser().getUsername()),
                    isFriend,
                    mainUser.getUserRole().equals(RoleType.ADMIN)
            );
            postsActions.add(postActionDto);
        }
        return ResponseEntity.ok(postsActions);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseMessage> deletePost(@RequestParam("id") long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        postService.deletePost(userDetails.getUsername(), id);
        return ResponseEntity.ok(DtoService.responseMessage("ok"));
    }

    @PostMapping(value = "/action")
    public ResponseEntity actionPost(@RequestBody ActivityDto dto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userService.findUserByUsername(userDetails.getUsername());
        Post post = postService.findPostById(dto.getId());
        activityService.save(post, user);
        return ResponseEntity.ok(DtoService.responseMessage("ok"));
    }
}

