package dbp.proyecto.song.dto;

import lombok.Data;

import java.util.List;

@Data
public class SongResponseForPostDto {
    private String title;
    private String coverImageUrl;
    private List<String> artistsNames;
    private String duration;
    private String genre;
    private String spotifyUrl;
}
