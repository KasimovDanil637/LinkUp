package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.dto.request.SignUpRequest;
import web.dto.request.UpdateUserDto;
import web.exception.DataException;
import web.model.Avatar;
import web.model.RoleType;
import web.model.User;
import web.model.UserInfo;
import web.repositories.SearchRepository;
import web.repositories.UserInfoRepository;
import web.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean existsUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.existsUserByUsername(username);
    }
    public User findUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new DataException("User not found"));
    }
    public Boolean existsUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.existsUserByEmail(email);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public void updateUser(String username, UpdateUserDto newUser) {
        User user = findUserByUsername(username);
        user.setName(newUser.getName());
        user.setSecondName(newUser.getSecondName());
        userRepository.save(user);
    }
    public UserInfo findInfoByUser(User user) {
        return userInfoRepository
                .findUserInfoByUser(user)
                .orElseThrow(() -> new DataException("UserInfo not found"));
    }
    public void saveUserInfo(UserInfo info) { userInfoRepository.save(info);}
    public void updateUserInfo(String username, UpdateUserDto newUser) {
        User user = findUserByUsername(username);
        UserInfo info = findInfoByUser(user);
        info.setCity(newUser.getCity());
        info.setBirthday(newUser.getBirthday());
        info.setEducation(newUser.getEducation());
        userInfoRepository.save(info);}

    public List<User> searchUser(String username) { return searchRepository.searchUser(username);}

    public void registry(SignUpRequest signUpRequest) {
        User user = new User();
        User admin = findUserByUsername("admin");
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setSecondName(signUpRequest.getSecondName());
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        Avatar avatar = new Avatar();
        avatar.setUser(user);
        avatar.setData(avatarService.getAvatarByUser(admin).getData());
        RoleType roleType = user.getUsername().equals("admin") ? RoleType.ADMIN : RoleType.USER;
        user.setUserRole(roleType);
        saveUser(user);
        saveUserInfo(userInfo);
        avatarService.saveAvatar(avatar);
    }
}
