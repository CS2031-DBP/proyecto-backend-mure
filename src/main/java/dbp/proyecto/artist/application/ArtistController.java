package dbp.proyecto.artist.application;

import dbp.proyecto.artist.domain.ArtistService;
import dbp.proyecto.artist.dto.ArtistBodyDTO;
import dbp.proyecto.artist.dto.ArtistBodyInfoDTO;
import dbp.proyecto.artist.dto.ArtistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> getAllArtists() {
        List<ArtistResponseDTO> artists = artistService.findAllArtists();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) {
        ArtistResponseDTO artist = artistService.findById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping
    public ResponseEntity<List<String>> createArtists(@RequestBody List<ArtistBodyDTO> artistBodyDTOs) {
        List<String> artistUrls = artistService.createArtists(artistBodyDTOs);
        return ResponseEntity.created(URI.create("/artist")).body(artistUrls);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArtistInfo(@PathVariable Long id, @RequestBody ArtistBodyInfoDTO updatedArtistResponseDTO) {
        artistService.updateArtistInfo(id, updatedArtistResponseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
