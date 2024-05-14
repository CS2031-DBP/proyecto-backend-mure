package dbp.proyecto.post.application;

import dbp.proyecto.post.domain.PostService;
import dbp.proyecto.post.dtos.PostResponseDTO;
import dbp.proyecto.song.Song;
import dbp.proyecto.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/song")
    public ResponseEntity<List<PostResponseDTO>> getPostBySong(@RequestBody Song song) {
        return ResponseEntity.ok(postService.getPostBySong(song));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<PostResponseDTO> getPostByAuthor(@RequestBody User author) {
        return ResponseEntity.ok(postService.getPostByAuthor(author));
    }

    @PatchMapping("/media/{id}")
    public ResponseEntity<Void> changeMedia(@PathVariable Long id, @RequestBody String media) {
        postService.changeMedia(id, media);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/content/{id}")
    public ResponseEntity<Void> changeContent(@PathVariable Long id, @RequestBody String content) {
        postService.changeContent(id, content);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/song/{id}")
    public ResponseEntity<Void> changeSong(@PathVariable Long id, @RequestBody Song song) {
        postService.changeSong(id, song);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
