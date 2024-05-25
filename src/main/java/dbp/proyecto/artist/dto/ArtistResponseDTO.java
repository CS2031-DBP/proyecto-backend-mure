package dbp.proyecto.artist.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArtistResponseDTO {
    private String name;
    private String description;
    private Date birthDate;
    private Boolean verified;
    private List<String> albumsTitles;
    private List<String> songTitles;
}
