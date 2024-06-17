package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Friends;
import web.model.User;
import web.repositories.FriendRepository;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserService userService;

    public void saveFriend(Friends friends) { friendRepository.save(friends);}

    public List<Friends> findUserFriendsNotDeleted(User user) { return friendRepository.findUserFriendsNotDeleted(user); }
    public List<Friends> findFriendsNotUser(User user, User friend) { return friendRepository.findFriendsNotUser(user, friend); }

    public Friends getFriends(User user, User friend){ return friendRepository.getFriendsByTwoUsers(user, friend);}

    public void deleteFriend(String username, String userName){
        User friend = userService.findUserByUsername(username);
        User user = userService.findUserByUsername(userName);
        Friends friends = getFriends(user, friend);
        friends.setDeleted(true);
        friendRepository.save(friends);
    }
}
