package dbp.proyecto.song;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String album;

    private Long likesNum;

//    private List<String> artists; Marca error D:

    private Date releaseDate;

    private String genre;

    private Duration duration;

    private Integer timesPlayed;

    private String coverImage;
}
