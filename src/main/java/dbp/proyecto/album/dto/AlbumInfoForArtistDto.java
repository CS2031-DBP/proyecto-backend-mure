package dbp.proyecto.album.dto;

import lombok.Data;


@Data
public class AlbumInfoForArtistDto {
    private Long id;
    private String title;
    private String description;
    private Integer songsCount;
    private String totalDuration;
}
