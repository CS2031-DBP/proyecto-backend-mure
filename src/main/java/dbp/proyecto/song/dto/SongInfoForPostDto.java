package dbp.proyecto.song.dto;

import lombok.Data;

@Data
public class SongInfoForPostDto {
    private String title;
    private String url;
    private String coverUrl;
    private String artist;
    private String duration;
    private String genre;
    private String link;
}
