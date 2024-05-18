package dbp.proyecto.album.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AlbumDTO {
    private Long id;
    private String title;
    private String description;
    private Timestamp duration;
}
