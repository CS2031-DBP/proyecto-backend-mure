package dbp.proyecto.song.application;

import dbp.proyecto.song.domain.SongService;
import dbp.proyecto.song.dto.SongBodyDTO;
import dbp.proyecto.song.dto.SongInfoForAlbumDTO;
import dbp.proyecto.song.dto.SongInfoForArtistDTO;
import dbp.proyecto.song.dto.SongResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @GetMapping("/title")
    public ResponseEntity<SongResponseDTO> getSongByTitle(@RequestParam String title) {
        return ResponseEntity.ok(songService.getSongByTitle(title));
    }

    @GetMapping("/genre")
    public ResponseEntity<List<SongResponseDTO>> getSongsByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(songService.getSongsByGenre(genre));
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<SongInfoForArtistDTO>> getSongsByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(songService.getSongsByArtist(artistId));
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<SongInfoForAlbumDTO>> getSongsByAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ok(songService.getSongsByAlbum(albumId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SongResponseDTO>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @PostMapping
    public ResponseEntity<Void> createSongs(@RequestBody List<SongBodyDTO> songBodyDTOs) {
        songService.createSongs(songBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/coverImage/{id}")
    public ResponseEntity<Void> putSongCoverImage(@PathVariable Long id, @RequestParam String coverImage) {
        songService.updateCoverImage(coverImage, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

}
