package dbp.proyecto.artist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ArtistBodyInfoDTO {
    private String name;
    private String description;
    private Date birthDate;
    private Boolean verified;
}
