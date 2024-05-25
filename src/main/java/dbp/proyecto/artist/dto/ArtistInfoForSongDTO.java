package dbp.proyecto.artist.dto;

import lombok.Data;

@Data
public class ArtistInfoForSongDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean verified;
}
