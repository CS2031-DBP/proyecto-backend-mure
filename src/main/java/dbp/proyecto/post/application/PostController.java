package dbp.proyecto.post.application;

import dbp.proyecto.post.domain.PostService;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.post.dtos.PostContentDTO;
import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    // falta getPostsMe()

    @PreAuthorize("hasRole('ROLE_USER')")
    // real paginacion
    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/song/{songId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsBySongId(@PathVariable Long songId) {
        return ResponseEntity.ok(postService.getPostsBySongId(songId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(postService.getPostsByAlbumId(albumId));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostBodyDTO postBodyDTO) {
        String uri = postService.createPost(postBodyDTO);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    // owner
    @PatchMapping("/media/{id}")
    public ResponseEntity<Void> changeMedia(@PathVariable Long id, @RequestBody PostMediaDTO media) {
        postService.changeMedia(id, media);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    // owner
    @PatchMapping("/content/{id}")
    public ResponseEntity<Void> updatePostContent(@PathVariable Long id, @RequestBody PostContentDTO content) {
        postService.changeContent(id, content.getSongId(), content.getAlbumId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    // is admin o resource owner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
