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
import web.dto.request.AnswerInviteDto;
import web.dto.response.ResponseMessage;
import web.model.Friends;
import web.model.User;
import web.service.AvatarService;
import web.service.FriendService;
import web.service.InviteService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/friend")
@CrossOrigin("${cors.hosts}")
@PreAuthorize("isAuthenticated()")
public class FriendsController {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private InviteService inviteService;

    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam("username") String username, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        username = username.isEmpty() ? userDetails.getUsername() : username;
        User user = userService.findUserByUsername(username);
        List<Friends> list = friendService.findUserFriendsNotDeleted(user);
        List<FriendDto> list1 = new ArrayList<>();
        for (Friends friend : list){
            FriendDto friendDto;
            if (friend.getFriendOne().equals(user)){
                friendDto = DtoService.friendDto(friend.getFriendTwo(),avatarService.getAvatarByUser(friend.getFriendTwo()));
            } else {
                friendDto = DtoService.friendDto(friend.getFriendOne(),avatarService.getAvatarByUser(friend.getFriendOne()));
            }
            list1.add(friendDto);
        }
        return ResponseEntity.ok(list1);
    }
    @GetMapping("/possible")
    public ResponseEntity<?> getPossible(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Friends> list = friendService.findUserFriendsNotDeleted(user);
        List<FriendDto> list1 = new ArrayList<>();
        Set<String> names = new HashSet<>();
        Set<String> names2 = new HashSet<>();
        for (Friends friend : list){
            names.add(friend.getFriendOne().getUsername());
            names.add(friend.getFriendTwo().getUsername());
        }
        FriendDto friendDto;
        List<Friends> friends;
        for (String username : names){
            if (!username.equals(user.getUsername())) {
                User client = userService.findUserByUsername(username);
                friends = friendService.findUserFriendsNotDeleted(client);
                for (Friends f : friends){
                    if (!names.contains(f.getFriendTwo().getUsername()) && !names2.contains(f.getFriendTwo().getUsername())){
                        names2.add(f.getFriendTwo().getUsername());
                        friendDto = DtoService.friendDto(f.getFriendTwo(),avatarService.getAvatarByUser(f.getFriendTwo()));
                        list1.add(friendDto);
                    }
                    if (!names.contains(f.getFriendOne().getUsername()) && !names2.contains(f.getFriendOne().getUsername())){
                        names2.add(f.getFriendOne().getUsername());
                        friendDto = DtoService.friendDto(f.getFriendOne(),avatarService.getAvatarByUser(f.getFriendOne()));
                        list1.add(friendDto);
                    }
                }
            }
        }
        return ResponseEntity.ok(list1);
    }

    @RequestMapping("/add")
    public ResponseEntity<ResponseMessage> addFriend(@RequestBody @Valid AnswerInviteDto answerInviteDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String userName = userDetails.getUsername();
        User friend = userService.findUserByUsername(answerInviteDto.getUsername());
        User user = userService.findUserByUsername(userName);
        inviteService.updateInvite(user, friend);
        if (!answerInviteDto.isFlag()){
            return ResponseEntity.ok(DtoService.responseMessage("cansel"));
        }
        Friends friends = friendService.getFriends(user,friend);
        if (friends != null){
            if (!friends.isDeleted()){
                return ResponseEntity.ok(DtoService.responseMessage("В друзьях"));
            }
            friends.setDeleted(false);
        } else {
            friends = new Friends();
            friends.setFriendTwo(friend);
            friends.setFriendOne(user);
        }
        friendService.saveFriend(friends);
        return ResponseEntity.ok(DtoService.responseMessage("accept"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteFriend(@RequestParam(value = "username") String username, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        String userName = userDetails.getUsername();
        friendService.deleteFriend(username, userName);
        return ResponseEntity.ok(DtoService.responseMessage("deleted"));
    }
}