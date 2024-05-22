package dbp.proyecto.artist.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ArtistBodyDTO {
    private String name;
    private String description;
    private Date birthDate;
    private Boolean verified;
    private List<Long> artistAlbums; // Corrected field name
    private List<Long> artistSongs; // Corrected field name
}
