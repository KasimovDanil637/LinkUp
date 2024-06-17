package web.controllers;

import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.dto.DtoService;
import web.dto.request.UpdateUserDto;
import web.dto.response.ResponseMessage;
import web.dto.response.UserDataDto;
import web.model.*;
import web.service.AvatarService;
import web.service.FriendService;
import web.service.InviteService;
import web.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/app")
@CrossOrigin("${cors.hosts}")
@PreAuthorize("isAuthenticated()")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private InviteService inviteService;

    @GetMapping("/user")
    public ResponseEntity<UserDataDto> getUser(@RequestParam(value = "username") String username, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        if (username.isEmpty()){
            username = userDetails.getUsername();
        }
        User user = userService.findUserByUsername(username);
        UserInfo info = userService.findInfoByUser(user);
        Avatar avatar = avatarService.getAvatarByUser(user);
        String mainUsername = userDetails.getUsername();
        User mainUser = userService.findUserByUsername(mainUsername);
        Friends isFriend = friendService.getFriends(mainUser, user);
        boolean flag = isFriend != null && !isFriend.isDeleted();
        Invites invites = inviteService.findByUserAndFriend(user, mainUser);
        return ResponseEntity.ok(DtoService.userDataDto(user, info, avatar, username.equals(mainUsername), flag, !(invites == null)));
    }

    @RequestMapping("/update")
    public ResponseEntity<?> getInfo(@RequestBody @Valid UpdateUserDto newUser, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String username = userDetails.getUsername();
        userService.updateUser(username, newUser);
        userService.updateUserInfo(username, newUser);
        return ResponseEntity.ok(DtoService.responseMessage(""));
    }

    @RequestMapping(value = "/updateAvatar")
    public ResponseEntity<ResponseMessage> updateAvatar(@RequestPart("file") Part file, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String username = userDetails.getUsername();
        avatarService.updateAvatar(file, userService.findUserByUsername(username));
        return ResponseEntity.ok(new ResponseMessage(""));
    }
}

