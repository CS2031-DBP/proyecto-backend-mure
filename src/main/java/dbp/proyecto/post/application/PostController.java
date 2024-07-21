package dbp.proyecto.post.application;

import dbp.proyecto.post.domain.PostService;
import dbp.proyecto.post.dtos.PostRequestDto;
import dbp.proyecto.post.dtos.PostResponseDto;
import dbp.proyecto.post.dtos.PostUpdateContentDto;
import dbp.proyecto.post.dtos.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(postService.getPostsByAlbumId(albumId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/song/{songId}")
    public ResponseEntity<List<PostResponseDto>> getPostsBySongId(@PathVariable Long songId) {
        return ResponseEntity.ok(postService.getPostsBySongId(songId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@RequestParam int page, @RequestParam int size) {
        Page<PostResponseDto> response = postService.getAllPosts(page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<Page<PostResponseDto>> getPostsByCurrentUser(@RequestParam int page, @RequestParam int size) {
        Page<PostResponseDto> response = postService.getPostsByCurrentUser(page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponseDto>> getPostsByUserId(@PathVariable Long userId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PostResponseDto> response = postService.getPostsByUserId(userId, pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> createPost(@ModelAttribute PostRequestDto postRequestDto) throws FileUploadException {
        postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/many")
    public ResponseEntity<Void> createPosts(@RequestBody List<PostRequestDto> postRequestDtos) throws FileUploadException {
        postService.createPosts(postRequestDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/content/{id}")
    public ResponseEntity<Void> updatePostContent(@PathVariable Long id, @RequestBody PostUpdateContentDto content) {
        postService.changeContent(id, content.getSongId(), content.getAlbumId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/dislike/{id}")
    public ResponseEntity<Void> dislikePost(@PathVariable Long id) {
        postService.dislikePost(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/like/{id}")
    public ResponseEntity<Void> likePost(@PathVariable Long id) {
        postService.likePost(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/media/{id}")
    public ResponseEntity<Void> changeMedia(@PathVariable Long id, @RequestBody PostUpdateDto media) {
        postService.changeMedia(id, media);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
