package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.dto.DtoService;
import web.dto.models.FriendDto;
import web.model.User;
import web.service.AvatarService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("${cors.hosts}")
@PreAuthorize("isAuthenticated()")
public class SearchController {

    @Autowired
    private UserService userService;
    @Autowired
    private AvatarService avatarService;

    @GetMapping(value = "/search")
    public ResponseEntity<List<FriendDto>> savePhoto(@RequestParam("username") String username, Authentication authentication) {
        List<User> usersCurrent = userService.searchUser(username);
        List<FriendDto> users = new ArrayList<>();
        for (User user : usersCurrent){
            FriendDto userDto = DtoService.friendDto(user, avatarService.getAvatarByUser(user));
            users.add(userDto);
        }
        return ResponseEntity.ok(users);
    }
}
