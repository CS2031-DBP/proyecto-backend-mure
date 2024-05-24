package dbp.proyecto.artist.application;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.domain.ArtistService;
import dbp.proyecto.artist.dto.ArtistBodyDTO;
import dbp.proyecto.artist.dto.ArtistInfoForSongDTO;
import dbp.proyecto.artist.dto.ArtistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @GetMapping("/verified")
    public ResponseEntity<List<ArtistResponseDTO>> getVerifiedArtists() {
        return ResponseEntity.ok(artistService.findVerifiedArtists());
    }

    @GetMapping("/name")
    public ResponseEntity<ArtistResponseDTO> getArtistByName(@RequestParam String name) {
        return ResponseEntity.ok(artistService.getArtistByName(name));
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<List<ArtistInfoForSongDTO>> getArtistBySong(@PathVariable Long songId) {
        return ResponseEntity.ok(artistService.getArtistsBySong(songId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArtistResponseDTO>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @PostMapping
    public ResponseEntity<Void> createArtists(@RequestBody List<ArtistBodyDTO> artistBodyDTOs) {
        artistService.createArtists(artistBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateArtistInfo(@PathVariable Long id, @RequestBody Artist updatedArtist) {
        artistService.updateArtistInfo(id, updatedArtist);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
