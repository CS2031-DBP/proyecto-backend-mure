package dbp.proyecto.post.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequestDto {
    private Long userId;
    private Long songId;
    private Long albumId;
    private String description;
    private MultipartFile image;
    private MultipartFile audio;
}
