package dbp.proyecto.song.application;

import dbp.proyecto.song.domain.SongService;
import dbp.proyecto.song.dto.SongResponseForAlbumDto;
import dbp.proyecto.song.dto.SongResponseForArtistDto;
import dbp.proyecto.song.dto.SongRequestDto;
import dbp.proyecto.song.dto.SongResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDto> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<Page<SongResponseDto>> getSongsByTitle(@RequestParam String title, @RequestParam int page, @RequestParam int size) {
        Page<SongResponseDto> response = songService.getSongsByTitle(title, page, size);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/genre")
    public ResponseEntity<Page<SongResponseDto>> getSongsByGenre(@RequestParam String genre, @RequestParam int page, @RequestParam int size) {
        Page<SongResponseDto> response = songService.getSongsByGenre(genre, page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongResponseForArtistDto>> getSongsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(songService.getSongsByArtistId(artistId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/artistName")
    public ResponseEntity<Page<SongResponseDto>> getSongsByArtistName(@RequestParam String artistName, @RequestParam int page, @RequestParam int size) {
        Page<SongResponseDto> response = songService.getSongsByArtistName(artistName, page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<SongResponseForAlbumDto>> getSongsByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(songService.getSongsByAlbumId(albumId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/songs/all")
    public ResponseEntity<Page<SongResponseDto>> getAllSongs(@RequestParam int page, @RequestParam int size) {
        Page<SongResponseDto> response = songService.getAllSongs(page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createSongs(@RequestBody List<SongRequestDto> songRequestDtos) {
        songService.createSongs(songRequestDtos);
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
