package dbp.proyecto.song.application;

import dbp.proyecto.song.domain.SongService;
import dbp.proyecto.song.dto.SongBodyDTO;
import dbp.proyecto.song.dto.SongInfoForAlbumDTO;
import dbp.proyecto.song.dto.SongInfoForArtistDTO;
import dbp.proyecto.song.dto.SongResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<SongResponseDTO> getSongByTitle(@RequestParam String title) {
        return ResponseEntity.ok(songService.getSongByTitle(title));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/genre")
    public ResponseEntity<List<SongResponseDTO>> getSongsByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(songService.getSongsByGenre(genre));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongInfoForArtistDTO>> getSongsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(songService.getSongsByArtistId(artistId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/artistName")
    public ResponseEntity<List<SongInfoForArtistDTO>> getSongsByArtistName(@RequestParam String artistName) {
        return ResponseEntity.ok(songService.getSongsByArtistName(artistName));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<SongInfoForAlbumDTO>> getSongsByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(songService.getSongsByAlbumId(albumId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<SongResponseDTO>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createSongs(@RequestBody List<SongBodyDTO> songBodyDTOs) {
        songService.createSongs(songBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/coverImage/{id}")
    public ResponseEntity<Void> putSongCoverImage(@PathVariable Long id, @RequestParam String coverImage) {
        songService.updateCoverImage(coverImage, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

}
