package dbp.proyecto.album.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Data
public class AlbumBodyDTO {
    private String title;
    private String description;
    private Duration duration;
    private List<Long> artistsIds;
    private List<Long> songsIds;
}
