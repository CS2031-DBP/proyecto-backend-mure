package dbp.proyecto.song.dto;

import dbp.proyecto.album.domain.Album;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;

@Data
public class SongBodyDTO {
    private String title;
    private Album album;
    private Date releaseDate;
    private String genre;
    private Duration duration;
}
