package dbp.proyecto.post.application;

import dbp.proyecto.post.domain.PostService;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.post.dtos.PostContentDTO;
import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsBySongId(@PathVariable Long songId) {
        return ResponseEntity.ok(postService.getPostsBySongId(songId));
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(postService.getPostsByAlbumId(albumId));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostBodyDTO postBodyDTO) {
        String uri = postService.createPost(postBodyDTO);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PatchMapping("/media/{id}")
    public ResponseEntity<Void> changeMedia(@PathVariable Long id, @RequestBody PostMediaDTO media) {
        postService.changeMedia(id, media);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/content/{id}")
    public ResponseEntity<Void> updatePostContent(@PathVariable Long id, @RequestBody PostContentDTO content) {
        postService.changeContent(id, content.getSongId(), content.getAlbumId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
