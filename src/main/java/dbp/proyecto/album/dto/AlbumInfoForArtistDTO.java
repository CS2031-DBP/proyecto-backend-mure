package dbp.proyecto.album.dto;

import lombok.Data;


@Data
public class AlbumInfoForArtistDTO {
    private Long id;
    private String title;
    private String description;
    private Integer songsCount;
    private String totalDuration;
}
