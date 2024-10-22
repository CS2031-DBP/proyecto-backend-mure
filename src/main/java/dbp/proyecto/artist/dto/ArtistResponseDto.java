package dbp.proyecto.artist.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArtistResponseDto {
    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private Boolean verified;
    private List<String> albumsTitles;
    private List<String> songTitles;
    private String imageUrl;
    private List<Long> albumsIds;
    private List<Long> songsIds;
}
