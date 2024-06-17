package web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValueDto {
    @NotNull(message = "{null.message}")
    private String value;
}
