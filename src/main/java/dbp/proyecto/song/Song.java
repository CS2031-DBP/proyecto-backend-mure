package dbp.proyecto.song;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;
import java.util.Date;
import java.util.List;

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

    public Song() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Long getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Long likesNum) {
        this.likesNum = likesNum;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Integer timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
