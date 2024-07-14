package dbp.proyecto.mediaStorage.domain;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaStorageService {
    private final AmazonS3 s3Client;

    @Value("${amazonS3.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile multipartFile) throws FileUploadException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = dateTimeFormatter.format(LocalDate.now());
        String filePath;

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            String uniqueId = UUID.randomUUID().toString();
            filePath = todayDate + "_" + uniqueId + multipartFile.getOriginalFilename();
            s3Client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
            return s3Client.getUrl(bucketName, filePath).toString();
        } catch (IOException e) {
            throw new FileUploadException("Error occurred in file upload ==> " + e.getMessage());
        }
    }
}
