package dbp.proyecto.album.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Data
public class AlbumBodyDTO {
    private String title;
    private String description;
    private Long artistId;
    private List<Long> songsIds;
}
