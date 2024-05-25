package dbp.proyecto.story.application;

import dbp.proyecto.story.domain.StoryService;
import dbp.proyecto.story.dto.StoryBodyDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseDTO> getStoryById(@PathVariable Long id) {
        StoryResponseDTO story = storyService.getStoryById(id);
        return ResponseEntity.ok(story);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StoryResponseDTO>> getStoriesByUserId(@PathVariable Long userId) {
        List<StoryResponseDTO> stories = storyService.getStoriesByUserId(userId);
        return ResponseEntity.ok(stories);
    }

    // GET STORIES ME

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<List<StoryResponseDTO>> getAllStories() {
        List<StoryResponseDTO> stories = storyService.getAllStories();
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/song/{songId}")
    public ResponseEntity<List<StoryResponseDTO>> getStoriesBySongId(@PathVariable Long songId) {
        List<StoryResponseDTO> stories = storyService.getStoriesBySongId(songId);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    // current user
    @GetMapping("/time")
    public ResponseEntity<List<StoryResponseDTO>> getStoriesByCreatedAtLessThanEqualAndExpiresAtGreaterThanEqual(@RequestParam LocalDateTime createdAt, @RequestParam LocalDateTime expiresAt) {
        List<StoryResponseDTO> stories = storyService.getStoriesByCreatedAtLessThanEqualAndExpiresAtGreaterThanEqual(createdAt, expiresAt);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<String> createStory(@RequestBody StoryBodyDTO storyBodyDTO) {
        String uri = storyService.createStory(storyBodyDTO);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    // is admin or owner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}
