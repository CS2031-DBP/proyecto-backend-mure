package dbp.proyecto.song.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class SongBodyDTO {
    private String title;
    private List<Long> artistsIds;
    private Date releaseDate;
    private String genre;
    private String duration;
}
