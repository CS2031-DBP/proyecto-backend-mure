package dbp.proyecto.album.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlbumInfoForPostDto {
    private Long id;
    private String title;
    private String spotifyUrl;
    private String coverImageUrl;
    private String artist;
    private String duration;
    private List<String> songs;
}
