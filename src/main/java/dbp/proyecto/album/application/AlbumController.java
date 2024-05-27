package dbp.proyecto.album.application;

import dbp.proyecto.album.domain.AlbumService;
import dbp.proyecto.album.dto.AlbumBodyDTO;
import dbp.proyecto.album.dto.AlbumInfoForArtistDTO;
import dbp.proyecto.album.dto.AlbumResponseDTO;
import dbp.proyecto.album.dto.AlbumUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}") // ✔️
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/title") // ✔️
    public ResponseEntity<AlbumResponseDTO> getAlbumByTitle(@RequestParam String title) {
        return ResponseEntity.ok(albumService.getAlbumByTitle(title));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/artist/{artistId}") // ✔️
    public ResponseEntity<List<AlbumInfoForArtistDTO>> getAlbumsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(albumService.getAlbumsByArtistId(artistId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/artistName") // ✔️
    public ResponseEntity<List<AlbumInfoForArtistDTO>> getAlbumsByArtistName(@RequestParam String artistName) {
        return ResponseEntity.ok(albumService.getAlbumsByArtistName(artistName));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all") // ✔️
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping // ✔️
    public ResponseEntity<Void> createAlbums(@RequestBody List<AlbumBodyDTO> albumBodyDto) {
        albumService.createsAlbums(albumBodyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}") // ✔️
    public ResponseEntity<Void> updateAlbum(@PathVariable Long id, @RequestBody AlbumUpdateDTO albumUpdateDTO) {
        albumService.updateAlbum(id, albumUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}") // ✔️
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
