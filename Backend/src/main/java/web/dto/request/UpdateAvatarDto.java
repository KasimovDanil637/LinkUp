package web.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateAvatarDto {
    private MultipartFile avatar;
}
