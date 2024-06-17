package web.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {
    @NotNull(message = "{null.message}")
    private String username;
    @NotNull(message = "{null.message}")
    private String password;
}
