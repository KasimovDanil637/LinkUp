package web.dto.models;

import lombok.Getter;
import lombok.Setter;
import web.dto.models.UserDto;

import java.sql.Timestamp;

@Getter
@Setter
public class PostDto {

    private Long id;
    private UserDto user;
    private String message;
    private byte[] photo;
    private Timestamp time;
    private boolean isDeleted;
}
