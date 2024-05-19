package dbp.proyecto.playlist.application;

import dbp.proyecto.playlist.domain.PlaylistService;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/{name}") //Lista¿
    public ResponseEntity<List<PlaylistResponseDTO>> getPlaylistByName(@PathVariable String name) {
        List<PlaylistResponseDTO> playlist = playlistService.getPlaylistByName(name);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable Long id) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/songs")
    public ResponseEntity<List<Song>> getSongs() {
        List<Song> songs = playlistService.getSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = playlistService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/song")
    public ResponseEntity<Void> addSong(@RequestBody Song song) {
        playlistService.addSong(song);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/song/delete")
    public ResponseEntity<Void> deleteSong(@RequestBody Song song) {
        playlistService.deleteSong(song);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/users") //todo cambiar a usuaro
    public ResponseEntity<Void> addAuthors(@RequestBody User users) {
        playlistService.addAuthors(users);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/users/delete")
    public ResponseEntity<Void> deleteAuthors(@RequestBody User users) {
        playlistService.deleteAuthors(users);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePlaylistByName(@PathVariable String name) {
        playlistService.deletePlaylistByName(name);
        return ResponseEntity.ok().build();
    }
}
