package dbp.proyecto.album.application;

import dbp.proyecto.album.domain.AlbumService;
import dbp.proyecto.album.dto.AlbumDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        List<AlbumDto> albums = albumService.findAll();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable("id") Long id) {
        Optional<AlbumDto> album = albumService.findById(id);
        return album.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumDto albumDto) {
        AlbumDto savedAlbum = albumService.save(albumDto);
        return new ResponseEntity<>(savedAlbum, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable("id") Long id, @RequestBody AlbumDto updatedAlbumDto) {
        return albumService.findById(id)
                .map(album -> {
                    updatedAlbumDto.setId(album.getId());
                    AlbumDto savedAlbum = albumService.save(updatedAlbumDto);
                    return new ResponseEntity<>(savedAlbum, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        if (albumService.findById(id).isPresent()) {
            albumService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
