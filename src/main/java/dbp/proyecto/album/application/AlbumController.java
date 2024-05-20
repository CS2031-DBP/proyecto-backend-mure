package dbp.proyecto.album.application;

import dbp.proyecto.album.domain.AlbumService;
import dbp.proyecto.album.dto.AlbumBodyDTO;
import dbp.proyecto.album.dto.AlbumResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(albumService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createAlbum(@RequestBody AlbumBodyDTO albumBodyDto) {
        String uri = albumService.createAlbum(albumBodyDto);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAlbum(@PathVariable Long id, @RequestBody AlbumBodyDTO updatedAlbumBodyDTO) {
        albumService.updateAlbum(id, updatedAlbumBodyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok().build();
    }
}
