package dbp.proyecto.song.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SongResponseDto {
    private String id;
    private String title;
    private String genre;
    private Integer likes;
    private Integer timesPlayed;
    private LocalDate releaseDate;
    private String duration;
    private String coverImage;
    private String albumTitle;
    private List<String> artistsNames;
    private String link;
    private List<Long> artistsIds;
    private Long albumId;
}
