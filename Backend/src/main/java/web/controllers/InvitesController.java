package web.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.dto.DtoService;
import web.dto.models.FriendDto;
import web.dto.request.ValueDto;
import web.dto.response.ResponseMessage;
import web.model.Friends;
import web.model.Invites;
import web.model.User;
import web.service.AvatarService;
import web.service.FriendService;
import web.service.InviteService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/invite")
@CrossOrigin("${cors.hosts}")
@PreAuthorize("isAuthenticated()")
public class InvitesController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private InviteService inviteService;

    @GetMapping("/list")
    public ResponseEntity<?> getList(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);
        List<Invites> list = inviteService.findInvitesUserNotAccepted(user);
        List<FriendDto> list1 = new ArrayList<>();
        for (Invites friend : list){
            list1.add(DtoService.friendDto(friend.getFriend(),  avatarService.getAvatarByUser(friend.getFriend())));
        }
        return ResponseEntity.ok(list1);
    }

    @RequestMapping("/add")
    public ResponseEntity<ResponseMessage> addFriend(@RequestBody @Valid ValueDto valueDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String userName = userDetails.getUsername();
        User friend = userService.findUserByUsername(valueDto.getValue());
        User user = userService.findUserByUsername(userName);
        Friends friends = friendService.getFriends(user, friend);
        if (friends != null && !friends.isDeleted()){
            return ResponseEntity.ok(DtoService.responseMessage("В друзьях"));
        };
        inviteService.saveInvite(user, friend);
        return ResponseEntity.ok(DtoService.responseMessage("ok"));
    }
}