package web.dto.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Setter
@Getter
public class UserInfoDto {
    private Long id;
    private UserDto user;
    private String city;
    private Date birthday;
    private String education;
}
