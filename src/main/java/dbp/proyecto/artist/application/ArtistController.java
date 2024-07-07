package dbp.proyecto.artist.application;

import dbp.proyecto.artist.domain.ArtistService;
import dbp.proyecto.artist.dto.ArtistInfoForSongDto;
import dbp.proyecto.artist.dto.ArtistRequestDto;
import dbp.proyecto.artist.dto.ArtistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/verified")
    public ResponseEntity<List<ArtistResponseDto>> getVerifiedArtists() {
        return ResponseEntity.ok(artistService.findVerifiedArtists());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/name")
    public ResponseEntity<Page<ArtistResponseDto>> getArtistsByName(@RequestParam String name, @RequestParam int page,
                                                                    @RequestParam int size) {
        return ResponseEntity.ok(artistService.getArtistsByName(name, page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/songs/{songId}")
    public ResponseEntity<List<ArtistInfoForSongDto>> getArtistsBySongId(@PathVariable Long songId) {
        return ResponseEntity.ok(artistService.getArtistsBySongId(songId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/songTitle")
    public ResponseEntity<List<ArtistInfoForSongDto>> getArtistsBySongTitle(@RequestParam String title) {
        return ResponseEntity.ok(artistService.getArtistsBySongTitle(title));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ArtistResponseDto>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createArtists(@RequestBody List<ArtistRequestDto> artistRequestDtos) {
        artistService.createArtists(artistRequestDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateArtistInfo(@PathVariable Long id, @RequestBody ArtistRequestDto updatedArtist) {
        artistService.updateArtistInfo(id, updatedArtist);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
