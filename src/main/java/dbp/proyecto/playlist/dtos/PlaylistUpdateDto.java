package dbp.proyecto.playlist.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlaylistUpdateDto {
    private Long id;
    private String name;
    private MultipartFile coverImage;
}
