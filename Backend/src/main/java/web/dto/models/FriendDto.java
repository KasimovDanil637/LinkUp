package web.dto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.dto.models.AvatarDto;
import web.dto.models.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    private UserDto friend;
    private AvatarDto avatar;
}
