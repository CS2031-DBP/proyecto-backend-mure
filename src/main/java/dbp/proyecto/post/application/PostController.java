package dbp.proyecto.post.application;

import dbp.proyecto.post.domain.PostService;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostBodyDTO postBodyDTO, @RequestParam Long userId) {
        String uri = postService.createPost(postBodyDTO, userId);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PatchMapping("/media/{id}")
    public ResponseEntity<Void> changeMedia(@PathVariable Long id, @RequestBody PostMediaDTO media) {
        postService.changeMedia(id, media);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/description/{id}")
    public ResponseEntity<Void> changeDescription(@PathVariable Long id, @RequestParam String description) {
        postService.changeDescription(id, description);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/song/{id}")
    public ResponseEntity<Void> changeSong(@PathVariable Long id, @RequestParam Long songId) {
        postService.changeSong(id, songId);
        return ResponseEntity.ok().build();
    }

/*    @PatchMapping("/playlist/{id}")
    public ResponseEntity<Void> changePlaylist(@PathVariable Long id, @RequestParam Long playlistId) {
        postService.changePlaylist(id, playlistId);
        return ResponseEntity.ok().build();
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
