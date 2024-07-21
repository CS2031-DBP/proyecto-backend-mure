package dbp.proyecto.playlist.application;

import dbp.proyecto.playlist.domain.PlaylistService;
import dbp.proyecto.playlist.dtos.PlaylistBodyDTO;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
import dbp.proyecto.playlist.dtos.PlaylistUpdateDto;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<PlaylistResponseDTO>> getPlaylistsByUserId(@PathVariable Long id, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PlaylistResponseDTO> playlists = playlistService.getPlaylistsByUserId(id, pageable);
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<Page<PlaylistResponseDTO>> getPlaylistsByCurrentUser(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PlaylistResponseDTO> playlists = playlistService.getPlaylistsByCurrentUser(pageable);
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<PlaylistResponseDTO>> getAllPlaylists() {
        List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/image")
    public ResponseEntity<Void> createPlaylist(@ModelAttribute PlaylistBodyDTO playlistBodyDTO) throws FileUploadException {
        playlistService.createPlaylist(playlistBodyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> createPlaylists(@RequestBody List<PlaylistBodyDTO> playlistBodyDTOs) {
        playlistService.createPlaylists(playlistBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity<Void> updateNameAndCoverImage(@ModelAttribute PlaylistUpdateDto playlistUpdateDto) throws FileUploadException {
        playlistService.updateNameAndCoverImage(playlistUpdateDto);
        return ResponseEntity.noContent().build();
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
