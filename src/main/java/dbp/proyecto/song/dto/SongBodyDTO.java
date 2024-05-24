package dbp.proyecto.song.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SongBodyDTO {
    private String title;
    private List<Long> artistsIds;
    private LocalDate releaseDate;
    private String genre;
    private String duration;
    private String coverImage;
}
