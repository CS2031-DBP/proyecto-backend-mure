package dbp.proyecto.song.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SongInfoForArtistDTO {
    private String title;
    private String genre;
    private Integer likes;
    private LocalDate releaseDate;
    private String duration;
    private String albumTitle;
}
