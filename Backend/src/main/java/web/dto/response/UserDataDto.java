package web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.dto.models.AvatarDto;
import web.dto.models.UserDto;
import web.dto.models.UserInfoDto;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class UserDataDto {
    private UserDto user;
    private UserInfoDto info;
    private AvatarDto avatar;
    private boolean isAuth;
    private boolean isFriend;
    private boolean isInvited;
}
