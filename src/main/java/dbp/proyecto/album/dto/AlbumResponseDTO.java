package dbp.proyecto.album.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlbumResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer songsCount;
    private String totalDuration;
    private String artistName;
    private List<String> songsTitles;
}
