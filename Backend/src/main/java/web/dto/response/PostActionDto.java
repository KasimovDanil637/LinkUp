package web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import web.dto.models.AvatarDto;
import web.dto.models.PostDto;
import web.dto.models.UserDto;
import web.model.Avatar;

@AllArgsConstructor
@Getter
@Setter
public class PostActionDto {
    private PostDto post;
    private int likes;
    private boolean isAction;
    private AvatarDto avatar;
    private boolean isAuth;
    private boolean isFriend;
    private boolean isAdmin;
}
