package dbp.proyecto.song.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SongRequestDto {
    private String title;
    private List<Long> artistsIds;
    private LocalDate releaseDate;
    private String genre;
    private String duration;
    private String coverImageUrl;
    private String spotifyUrl;
    private String spotifyPreviewUrl;
}
