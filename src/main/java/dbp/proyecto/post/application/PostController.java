package dbp.proyecto.post.application;

import dbp.proyecto.post.domain.PostService;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.post.dtos.PostContentDTO;
import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<List<PostResponseDTO>> getPostsByCurrentUser() {
        return ResponseEntity.ok(postService.getPostsByCurrentUser());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<Page<PostResponseDTO>> getAllPosts(@RequestParam int page, @RequestParam int size) {
         Page<PostResponseDTO> response = postService.getAllPosts(page, size);
         return ResponseEntity.ok(response);
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
    public ResponseEntity<Void> createPosts(@RequestBody List<PostBodyDTO> postBodyDTOs) {
        postService.createPosts(postBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/media/{id}")
    public ResponseEntity<Void> changeMedia(@PathVariable Long id, @RequestBody PostMediaDTO media) {
        postService.changeMedia(id, media);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/content/{id}")
    public ResponseEntity<Void> updatePostContent(@PathVariable Long id, @RequestBody PostContentDTO content) {
        postService.changeContent(id, content.getSongId(), content.getAlbumId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/like/{id}")
    public ResponseEntity<Void> likePost(@PathVariable Long id) {
        postService.likePost(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
