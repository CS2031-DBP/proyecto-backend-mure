package dbp.proyecto.song.application;

import dbp.proyecto.song.domain.SongService;
import dbp.proyecto.song.dto.SongBodyDTO;
import dbp.proyecto.song.dto.SongInfoForAlbumDTO;
import dbp.proyecto.song.dto.SongInfoForArtistDTO;
import dbp.proyecto.song.dto.SongResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<Page<SongResponseDTO>> getSongsByTitleWithPagination(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SongResponseDTO> songsPage = songService.getSongsByTitleWithPagination(title, pageable);
        return ResponseEntity.ok(songsPage);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/genre")
    public ResponseEntity<Page<SongResponseDTO>> getSongsByGenre(@RequestParam String genre, @RequestParam int page, @RequestParam int size) {
        Page<SongResponseDTO> response = songService.getSongsByGenre(genre, page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongInfoForArtistDTO>> getSongsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(songService.getSongsByArtistId(artistId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/artistName")
    public ResponseEntity<Page<SongResponseDTO>> getSongsByArtistName(@RequestParam String artistName, @RequestParam int page, @RequestParam int size) {
        Page<SongResponseDTO> response = songService.getSongsByArtistName(artistName, page, size);
        return ResponseEntity.ok(response);
    }




    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<SongInfoForAlbumDTO>> getSongsByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(songService.getSongsByAlbumId(albumId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/songs/all")
    public ResponseEntity<Page<SongResponseDTO>> getAllSongs(@RequestParam int page, @RequestParam int size) {
        Page<SongResponseDTO> response = songService.getAllSongs(page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createSongs(@RequestBody List<SongBodyDTO> songBodyDTOs) {
        songService.createSongs(songBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/coverImage")
    public ResponseEntity<Void> updateCoverImage(@PathVariable Long id, @RequestParam String coverImage) {
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
