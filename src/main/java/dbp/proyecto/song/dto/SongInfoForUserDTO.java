package dbp.proyecto.song.dto;

import lombok.Data;

@Data
public class SongInfoForUserDTO {
    private Long id;
    private String title;
    private String genre;
    private String duration;
}
