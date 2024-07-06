package dbp.proyecto.media.domain;

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

@Service
@RequiredArgsConstructor
public class MediaService {
	private final AmazonS3 s3Client;

	@Value("${amazonS3.bucketName}")
	private String bucketName;

	public String uploadFile(MultipartFile multipartFile) throws FileUploadException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todayDate = dateTimeFormatter.format(LocalDate.now());
		String filePath = "";
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(multipartFile.getContentType());
			objectMetadata.setContentLength(multipartFile.getSize());
			filePath = todayDate + "/" + multipartFile.getOriginalFilename();
			s3Client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
			return filePath;
		} catch (IOException e) {
			throw new FileUploadException("Error occurred in file upload ==> " + e.getMessage());
		}
	}
}
