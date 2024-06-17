package web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AnswerInviteDto {
    @NotNull(message = "{null.message}")
    private String username;
    private boolean flag;
}
