package dbp.proyecto.story.application;


import dbp.proyecto.story.domain.StoryService;
import dbp.proyecto.story.dto.StoryPatchDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.user.dto.UserInfoForSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class StoryController {
    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseDTO> getStoryById(@PathVariable Long id) {
        StoryResponseDTO story = storyService.getStoryById(id);
        return ResponseEntity.ok(story);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<StoryResponseDTO> getStoryByAuthor(@RequestBody UserInfoForSong author, @PathVariable Long id) {
        StoryResponseDTO story = storyService.getStoryByAuthor(author, id);
        return ResponseEntity.ok(story);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> changeContent(@PathVariable Long id, @RequestBody StoryPatchDTO data) {
        storyService.changeContent(id, data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.ok().build();
    }
}
