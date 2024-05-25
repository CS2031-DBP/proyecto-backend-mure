package dbp.proyecto.song.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SongInfoForAlbumDTO {
    private Long id;
    private String title;
    private String genre;
    private Integer likes;
    private String duration;
    private LocalDate releaseDate;
    private List<String> artistsNames;
}
