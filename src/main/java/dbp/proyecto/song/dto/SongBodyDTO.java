package dbp.proyecto.song.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SongBodyDTO {
    private String title;
    private String genre;
    private String duration;
    private String coverImage;
    private LocalDate releaseDate;
    private List<Long> artistsIds;
}
