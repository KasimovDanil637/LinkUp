package web.dto.request;

import lombok.Getter;

import java.sql.Date;

@Getter
public class UpdateUserDto {
    private String name;
    private String secondName;
    private Date birthday;
    private String city;
    private String education;
}
