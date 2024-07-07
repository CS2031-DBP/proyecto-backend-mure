package dbp.proyecto.album.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlbumInfoForPostDto {
    private String title;
    private String url;
    private String coverUrl;
    private String artist;
    private String duration;
    private List<String> songs;
}
