package dbp.proyecto.song;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String album;

    private Long likesNum;

    @ElementCollection //La anotación @ElementCollection en JPA indica que una colección de tipos simples o incrustables debe almacenarse en una tabla separada.
    private List<String> artists;

    private Date releaseDate;

    private String genre;

    private Duration duration;

    private Integer timesPlayed;

    private String coverImage;

    public Song() {}

    public Song(Long id, String title, String album, Long likesNum, List<String> artists, Date releaseDate, String genre, Duration duration, Integer timesPlayed, String coverImage) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.likesNum = likesNum;
        this.artists = artists;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.duration = duration;
        this.timesPlayed = timesPlayed;
        this.coverImage = coverImage;
    }
}
