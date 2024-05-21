package dbp.proyecto.song.application;

import dbp.proyecto.song.domain.SongService;

import dbp.proyecto.song.dto.SongBodyDTO;
import dbp.proyecto.song.dto.SongsResponseDTO;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<SongsResponseDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongsdtoById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<SongsResponseDTO>> getSongByTitle(@PathVariable String title) {
        return ResponseEntity.ok(songService.getSongsDtoByTittle(title));
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<SongsResponseDTO>> getSongByArtist(@PathVariable List<ArtistSongs> artist) {
        return ResponseEntity.ok(songService.getSongByArtists(artist));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<SongsResponseDTO>> getSongByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(songService.getSongByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<List<String>> postSongs(@RequestBody List<SongBodyDTO> songs) {
        List<String> savedSongUrls = songService.postSongs(songs);
        return ResponseEntity.ok().body(savedSongUrls);
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
