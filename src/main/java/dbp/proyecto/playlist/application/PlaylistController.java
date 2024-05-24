package dbp.proyecto.playlist.application;


import dbp.proyecto.playlist.domain.PlaylistService;
import dbp.proyecto.playlist.dtos.PlaylistBodyDTO;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable Long id) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistByName(@PathVariable String name) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistByName(name);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PlaylistResponseDTO>> getPlaylistsByUserId(@PathVariable Long id) {
        List<PlaylistResponseDTO> playlists = playlistService.getPlaylistsByUserId(id);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaylistResponseDTO>> getAllPlaylists() {
        List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PostMapping
    public ResponseEntity<Void> createPlaylist(@RequestBody PlaylistBodyDTO playlistBodyDTO) {
        playlistService.createPlaylist(playlistBodyDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/addSong/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long id, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(id, songId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/removeSong/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long id, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(id, songId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id, @PathVariable Long userId) {
        playlistService.deletePlaylist(id, userId);
        return ResponseEntity.ok().build();
    }
}
