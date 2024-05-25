package dbp.proyecto.artist.application;

import dbp.proyecto.artist.domain.ArtistService;
import dbp.proyecto.artist.dto.ArtistBodyDTO;
import dbp.proyecto.artist.dto.ArtistInfoForSongDTO;
import dbp.proyecto.artist.dto.ArtistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}") // ✔️
    public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/verified") // ✔️
    public ResponseEntity<List<ArtistResponseDTO>> getVerifiedArtists() {
        return ResponseEntity.ok(artistService.findVerifiedArtists());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/name") // ✔️
    public ResponseEntity<ArtistResponseDTO> getArtistByName(@RequestParam String name) {
        return ResponseEntity.ok(artistService.getArtistByName(name));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/songs/{songId}") // ✔️
    public ResponseEntity<List<ArtistInfoForSongDTO>> getArtistsBySongId(@PathVariable Long songId) {
        return ResponseEntity.ok(artistService.getArtistsBySongId(songId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/songTitle") // ✔️
    public ResponseEntity<List<ArtistInfoForSongDTO>> getArtistsBySongTitle(@RequestParam String title) {
        return ResponseEntity.ok(artistService.getArtistsBySongTitle(title));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all") // ✔️
    public ResponseEntity<List<ArtistResponseDTO>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping // ✔️
    public ResponseEntity<Void> createArtists(@RequestBody List<ArtistBodyDTO> artistBodyDTOs) {
        artistService.createArtists(artistBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}") // ✔️
    public ResponseEntity<Void> updateArtistInfo(@PathVariable Long id, @RequestBody ArtistBodyDTO updatedArtist) {
        artistService.updateArtistInfo(id, updatedArtist);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}") // ✔️
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
