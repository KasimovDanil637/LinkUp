package web.dto.models;

import lombok.*;
import web.model.RoleType;
import web.model.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String secondName;
    private String username;
    private String email;
    private RoleType userRole;
}
