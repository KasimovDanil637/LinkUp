package web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotNull(message = "{null.message}")
    private String name;
    @NotNull(message = "{null.message}")
    private String secondName;
    @NotNull(message = "{null.message}")
    private String username;
    @NotNull(message = "{null.message}")
    @Email(message = "{email.message}")
    private String email;
    @NotNull(message = "{null.message}")
    @Size(min = 6, max = 255, message = "{size.message}")
    private String password;
}
