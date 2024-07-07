package dbp.proyecto.song.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SongInfoForArtistDto {
    private Long id;
    private String title;
    private String genre;
    private Integer likes;
    private String duration;
    private LocalDate releaseDate;
    private String albumTitle;
}
