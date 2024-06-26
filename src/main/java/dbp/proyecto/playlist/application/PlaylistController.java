package dbp.proyecto.playlist.application;


import dbp.proyecto.playlist.domain.PlaylistService;
import dbp.proyecto.playlist.dtos.PlaylistBodyDTO;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable Long id) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/name")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistByName(@RequestParam String name) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistByName(name);
        return ResponseEntity.ok(playlist);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<PlaylistResponseDTO>> getPlaylistsByUserId(@PathVariable Long id) {
        List<PlaylistResponseDTO> playlists = playlistService.getPlaylistsByUserId(id);
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<List<PlaylistResponseDTO>> getPlaylistsByCurrentUser() {
        List<PlaylistResponseDTO> playlists = playlistService.getPlaylistsByCurrentUser();
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<PlaylistResponseDTO>> getAllPlaylists() {
        List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> createPlaylist(@RequestBody List<PlaylistBodyDTO> playlistBodyDTOs) {
        playlistService.createPlaylists(playlistBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}/addSong/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long id, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(id, songId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}/removeSong/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long id, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(id, songId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/isOwner/{playlistId}")
    public ResponseEntity<Boolean> isOwner(@PathVariable Long playlistId) {
        return ResponseEntity.ok(playlistService.isOwner(playlistId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
