package dbp.proyecto.playlist;

import dbp.proyecto.song.Song;
import dbp.proyecto.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    //falta tabla intermedia todo
    private List<User> authors;

    @ManyToMany
    private List<Song> songs;

    public Playlist(Long id, String name, List<User> authors, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.songs = songs;
    }

    public Playlist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getAuthors() {
        return authors;
    }

    public void setAuthors(List<User> authors) {
        this.authors = authors;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
