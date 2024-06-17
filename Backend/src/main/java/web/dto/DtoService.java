package web.dto;

import web.dto.models.*;
import web.dto.response.PostActionDto;
import web.dto.response.PostDataDto;
import web.dto.response.ResponseMessage;
import web.dto.response.UserDataDto;
import web.model.*;

import java.util.List;

public class DtoService {
    public static UserDto userDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setSecondName(user.getSecondName());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getUserRole());
        return userDto;
    }
    public static PostDto postDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setUser(DtoService.userDto(post.getUser()));
        postDto.setId(post.getId());
        postDto.setTime(post.getTime());
        postDto.setDeleted(post.isDeleted());
        postDto.setMessage(post.getMessage());
        postDto.setPhoto(post.getPhoto());
        return postDto;
    }
    public static AvatarDto avatarDto(Avatar avatar){
        AvatarDto avatarDto = new AvatarDto();
        avatarDto.setUser(DtoService.userDto(avatar.getUser()));
        avatarDto.setData(avatar.getData());
        avatarDto.setId(avatar.getId());
        return avatarDto;
    }
    public static UserInfoDto userInfoDto(UserInfo userInfo){
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userInfo.getId());
        userInfoDto.setUser(DtoService.userDto(userInfo.getUser()));
        userInfoDto.setBirthday(userInfo.getBirthday());
        userInfoDto.setCity(userInfo.getCity());
        userInfoDto.setEducation(userInfo.getEducation());
        return userInfoDto;
    }
    public static UserDataDto userDataDto(User user, UserInfo info, Avatar avatar, boolean isAuth, boolean isFriend, boolean isInvited) {
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setAuth(isAuth);
        userDataDto.setInfo(DtoService.userInfoDto(info));
        userDataDto.setInvited(isInvited);
        userDataDto.setAvatar(DtoService.avatarDto(avatar));
        userDataDto.setFriend(isFriend);
        userDataDto.setUser(DtoService.userDto(user));
        return userDataDto;
    }
    public static FriendDto friendDto(User user, Avatar avatar){
        FriendDto friendDto = new FriendDto();
        friendDto.setFriend(DtoService.userDto(user));
        friendDto.setAvatar(DtoService.avatarDto(avatar));
        return friendDto;
    }

    public static StatsDto statsDto(Stats stats){
       StatsDto statsDto = new StatsDto();
       statsDto.setId(stats.getId());
       statsDto.setDate(stats.getDate());
       statsDto.setUser(DtoService.userDto(stats.getUser()));
        return statsDto;
    }
    public static PostDataDto postDataDto(List<PostActionDto> post, boolean isAuth){
        PostDataDto postDataDto = new PostDataDto();
        postDataDto.setPost(post);
        postDataDto.setAuth(isAuth);
        return postDataDto;
    }
    public static ResponseMessage responseMessage(String message){
        return new ResponseMessage(message);
    }

}
