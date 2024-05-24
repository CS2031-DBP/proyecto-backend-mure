package dbp.proyecto.artist.dto;

import lombok.Data;

@Data
public class ArtistInfoForSongDTO {
    private String name;
    private String description;
    private Boolean verified;
}
