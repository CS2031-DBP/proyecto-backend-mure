package dbp.proyecto.playlist;

import dbp.proyecto.PlaylistSongs.PlaylistSongId;
import dbp.proyecto.PlaylistSongs.PlaylistSongs;
import dbp.proyecto.song.Song;
import dbp.proyecto.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<User> authors;

    @ManyToMany
    private List<PlaylistSongs> PlaylistSongs;

    public Playlist(Long id, String name, List<User> authors, List<PlaylistSongs> songs) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.PlaylistSongs = songs;
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

    public List<PlaylistSongs> getSongs() {
        return PlaylistSongs;
    }

    public void setSongs(List<PlaylistSongs> songs) {
        this.PlaylistSongs = songs;
    }
}
