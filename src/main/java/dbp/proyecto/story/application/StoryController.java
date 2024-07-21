package dbp.proyecto.story.application;

import dbp.proyecto.story.domain.StoryService;
import dbp.proyecto.story.dto.StoryBodyDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryService storyService;

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
    @GetMapping("/song/{songId}")
    public ResponseEntity<List<StoryResponseDTO>> getStoriesBySongId(@PathVariable Long songId) {
        List<StoryResponseDTO> stories = storyService.getStoriesBySongId(songId);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StoryResponseDTO>> getStoriesByUserId(@PathVariable Long userId) {
        List<StoryResponseDTO> stories = storyService.getStoriesByUserId(userId);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<Page<StoryResponseDTO>> getAllStories(@RequestParam int page, @RequestParam int size) {
        Page<StoryResponseDTO> response = storyService.getAllStories(page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<List<StoryResponseDTO>> getStoriesByCurrentUser() {
        List<StoryResponseDTO> stories = storyService.getStoriesByCurrentUser();
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> createStory(@RequestBody List<StoryBodyDTO> storyBodyDTOs) {
        storyService.createStories(storyBodyDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}
