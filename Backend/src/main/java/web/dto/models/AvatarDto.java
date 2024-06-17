package web.dto.models;

import lombok.Getter;
import lombok.Setter;
import web.dto.models.UserDto;

@Getter
@Setter
public class AvatarDto {
    private Long id;
    private UserDto user;
    private byte[] data;
}
