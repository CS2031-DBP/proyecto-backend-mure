package dbp.proyecto.album.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlbumResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer songsCount;
    private String totalDuration;
    private String artistName;
    private List<String> songsTitles;
    private String coverImageUrl;
    private String spotifyUrl;
    private Long artistId;
    private List<Long> songsIds;
}
