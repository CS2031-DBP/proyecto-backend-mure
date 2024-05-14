package dbp.proyecto.story.application;


import dbp.proyecto.story.domain.StoryService;
import dbp.proyecto.story.dto.StoryPatchDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseDTO> getStoryById(@RequestParam Long id) {
        StoryResponseDTO story = storyService.getStoryById(id);
        return ResponseEntity.ok(story);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<StoryResponseDTO> getStoryByAuthor(@RequestParam Long id, @RequestBody User author) {
        StoryResponseDTO story = storyService.getStoryByAuthor(author, id);
        return ResponseEntity.ok(story);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> changeContent(@RequestParam Long id, @RequestBody StoryPatchDTO data) {
        storyService.changeContent(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@RequestParam Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.ok().build();
    }
}
