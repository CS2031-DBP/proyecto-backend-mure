package dbp.proyecto.artist.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArtistRequestDto {
    private String name;
    private String nameNormalized;
    private String imageUrl;
    private String description;
    private LocalDate birthDate;
    private Boolean verified;
}
