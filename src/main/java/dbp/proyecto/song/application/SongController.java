package dbp.proyecto.song.application;

import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.domain.SongService;

import dbp.proyecto.song.dto.SongsDTO;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService service;

    @Autowired
    public SongController(SongService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongsDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSongsdtoById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<SongsDTO> getSongByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.getSongsDtoByTittle(title));
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<SongsDTO>> getSongByArtist(@PathVariable List<ArtistSongs> artist) {
        return ResponseEntity.ok(service.getSongByArtists(artist));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<SongsDTO>> getSongByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(service.getSongByGenre(genre));
    }

    @PostMapping
    public void postSong(@RequestBody Song song) {
        service.postSong(song);
    }

    @PutMapping("/coverImage/{id}")
    public ResponseEntity<Void> putSongCoverImage(@PathVariable Long id, @RequestParam String coverImage) {
        service.updateCoverImage(coverImage, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        service.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

}
