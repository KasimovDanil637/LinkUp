package web.service;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Avatar;
import web.model.User;
import web.repositories.AvatarRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    public void saveAvatar(Avatar avatar){ avatarRepository.save(avatar);}

    public void updateAvatar(Part file, User user) throws IOException{
        byte[] array = uploadFile(file);
        Avatar avatar = getAvatarByUser(user);
        avatar.setData(array);
        avatarRepository.save(avatar);
    }
    public Avatar getAvatarByUser(User user) { return avatarRepository.getAvatarByUser(user);}

    public byte[] uploadFile(Part filePart) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName.endsWith(".jpg")) {
            try (InputStream input = filePart.getInputStream()) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) != -1) {
                    output.write(buffer, 0, length);
                }
                return output.toByteArray();
            }
        } else {
            throw new IOException();
        }
    }
}
