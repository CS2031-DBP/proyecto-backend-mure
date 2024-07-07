package dbp.proyecto.song.dto;

import lombok.Data;

import java.util.List;

@Data
public class SongInfoForPostDto {
    private String title;
    private String url;
    private String coverUrl;
    private List<String> artistsNames;
    private String duration;
    private String genre;
    private String link;
}
