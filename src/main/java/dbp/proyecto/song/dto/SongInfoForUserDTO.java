package dbp.proyecto.song.dto;

import lombok.Data;

import java.util.List;

@Data
public class SongInfoForUserDTO {
    private Long id;
    private String title;
    private String genre;
    private String duration;
    private List<String> artistNames;
}
