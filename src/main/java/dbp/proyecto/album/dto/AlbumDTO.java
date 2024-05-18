package dbp.proyecto.album.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlbumDTO {
    private Long id;
    private String title;
    private String description;
    private Timestamp duration;
}
