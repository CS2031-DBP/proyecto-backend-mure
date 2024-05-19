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
    public ResponseEntity<PlaylistResponseDTO> getPlaylistByName(@PathVariable String name) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistByName(name);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable Long id) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<List<Song>> getSongs(@PathVariable Long id) {
        List<Song> songs = playlistService.getSongs(id);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<User>> getUsers(@PathVariable Long id) {
        List<User> users = playlistService.getUsers(id);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/song/{id}")
    public ResponseEntity<Void> addSong(@RequestBody Song song, @PathVariable Long id) {
        playlistService.addSong(song, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/song/delete/{id}")
    public ResponseEntity<Void> deleteSong(@RequestBody Song song, @PathVariable Long id) {
        playlistService.deleteSong(song, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Void> addAuthors(@RequestBody User users, @PathVariable Long id) {
        playlistService.addAuthors(users, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteAuthors(@RequestBody User users, @PathVariable Long id) {
        playlistService.deleteAuthors(users, id);
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
