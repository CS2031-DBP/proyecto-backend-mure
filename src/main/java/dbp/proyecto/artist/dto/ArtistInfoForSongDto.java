package dbp.proyecto.artist.dto;

import lombok.Data;

@Data
public class ArtistInfoForSongDto {
    private Long id;
    private String name;
    private String description;
    private Boolean verified;
}
