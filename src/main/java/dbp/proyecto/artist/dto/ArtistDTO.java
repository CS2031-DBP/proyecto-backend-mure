package dbp.proyecto.artist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArtistDTO {
    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private Boolean verified;
}
