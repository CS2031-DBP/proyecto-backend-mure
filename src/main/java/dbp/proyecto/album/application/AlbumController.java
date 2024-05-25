package dbp.proyecto.album.application;

import dbp.proyecto.album.domain.AlbumService;
import dbp.proyecto.album.dto.AlbumBodyDTO;
import dbp.proyecto.album.dto.AlbumInfoForArtistDTO;
import dbp.proyecto.album.dto.AlbumResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}") // ✔️
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @GetMapping("/title") // ✔️
    public ResponseEntity<AlbumResponseDTO> getAlbumByTitle(@RequestParam String title) {
        return ResponseEntity.ok(albumService.getAlbumByTitle(title));
    }

    @GetMapping("/artist/{artistId}")  // ✔️
    public ResponseEntity<List<AlbumInfoForArtistDTO>> getAlbumsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(albumService.getAlbumsByArtistId(artistId));
    }

    @GetMapping("/all") // ✔️
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @PostMapping // ✔️
    public ResponseEntity<Void> createAlbum(@RequestBody List<AlbumBodyDTO> albumBodyDto) {
        albumService.createAlbums(albumBodyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}") // ✔️
    public ResponseEntity<Void> updateAlbum(@PathVariable Long id, @RequestBody AlbumBodyDTO updatedAlbumBodyDTO) {
        albumService.updateAlbum(id, updatedAlbumBodyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}") // ✔️
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
