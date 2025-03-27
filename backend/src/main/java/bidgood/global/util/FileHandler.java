package bidgood.global.util;

import bidgood.global.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileHandler {
    @Value("{file.path}")
    private static String FOLDER;
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "png", "gif"};
    private final UuidGenerator uuidGenerator;

    public static boolean isAllowedExtension(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (allowedExt.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    public String saveFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if(isAllowedExtension(originalFilename)){
            UUID uuid = uuidGenerator.generate();
            String saveName = uuid.toString() + '_' + originalFilename;
            Path path = Paths.get(FOLDER + File.separator + saveName);
            try{
                Files.copy(file.getInputStream(), path);
            }catch (IOException e){
                throw new FileStorageException(originalFilename);
            }
            return path.toString();
        }
        return "FAIL";
    }
}
