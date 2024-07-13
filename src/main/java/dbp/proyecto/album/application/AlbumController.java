package dbp.proyecto.album.application;

import dbp.proyecto.album.domain.AlbumService;
import dbp.proyecto.album.dto.AlbumInfoForArtistDto;
import dbp.proyecto.album.dto.AlbumRequestDto;
import dbp.proyecto.album.dto.AlbumResponseDto;
import dbp.proyecto.album.dto.AlbumUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDto> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/title")
    public ResponseEntity<Page<AlbumResponseDto>> getAlbumsByTitle(@RequestParam String title,
                                                                   @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(albumService.getAlbumsByTitle(title, page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<AlbumInfoForArtistDto>> getAlbumsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(albumService.getAlbumsByArtistId(artistId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/artistName")
    public ResponseEntity<List<AlbumInfoForArtistDto>> getAlbumsByArtistName(@RequestParam String artistName,
                                                                             @RequestParam int page,
                                                                             @RequestParam int size) {
        return ResponseEntity.ok(albumService.getAlbumsByArtistName(artistName, page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<AlbumResponseDto>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createAlbum(@RequestBody List<AlbumRequestDto> albumRequestDtos) {
        albumService.createsAlbums(albumRequestDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAlbum(@PathVariable Long id, @RequestBody AlbumUpdateDto albumUpdateDTO) {
        albumService.updateAlbum(id, albumUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
