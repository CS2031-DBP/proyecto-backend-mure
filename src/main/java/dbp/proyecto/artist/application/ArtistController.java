package dbp.proyecto.artist.application;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.domain.ArtistService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<Artist>> artists() {
        List<Artist> artists = artistService.findAll();

        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> artist(@PathVariable("id") Long id) {
        Artist artist = artistService.findById(id);

        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Artist> artist(@RequestParam String name) {
        Artist artist = artistService.findByName(name);

        return new ResponseEntity<>(artist, HttpStatus.OK);
    }
}
