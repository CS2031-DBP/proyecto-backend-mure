package dbp.proyecto.playlist.application;

import dbp.proyecto.playlist.domain.PlaylistService;
import dbp.proyecto.playlist.dto.PlaylistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByUser(@PathVariable Long userId) {
        List<PlaylistDTO> playlists = playlistService.getPlaylistsByUser(userId);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/song")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsBySong(@RequestParam String songTitle) {
        List<PlaylistDTO> playlists = playlistService.getPlaylistsBySong(songTitle);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/name")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByName(@RequestParam String name) {
        List<PlaylistDTO> playlists = playlistService.getPlaylistsByName(name);
        return ResponseEntity.ok(playlists);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Void> createPlaylist(@PathVariable Long userId, @RequestBody PlaylistDTO playlistDTO) {
        playlistService.createPlaylist(playlistDTO, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playlistId}/song/{songId}")
    public ResponseEntity<Void> deleteSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.deleteSongFromPlaylist(playlistId, songId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{playlistId}/author/{userId}")
    public ResponseEntity<Void> addAuthorToPlaylist(@PathVariable Long playlistId, @PathVariable Long userId) {
        playlistService.addAuthorToPlaylist(playlistId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playlistId}/author/{userId}")
    public ResponseEntity<Void> deleteAuthorFromPlaylist(@PathVariable Long playlistId, @PathVariable Long userId) {
        playlistService.deleteAuthorFromPlaylist(playlistId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.ok().build();
    }
}