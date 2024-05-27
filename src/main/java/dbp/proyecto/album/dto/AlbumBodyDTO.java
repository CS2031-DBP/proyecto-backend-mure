package dbp.proyecto.album.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AlbumBodyDTO {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Long artistId;
    private List<Long> songsIds;
}
