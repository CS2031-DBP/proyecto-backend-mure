package dbp.proyecto.story.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.story.dto.StoryPatchDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StoryService {
    private final StoryRepository storyRepository;

    private final ModelMapper modelMapper;

    public StoryService(StoryRepository storyRepository, ModelMapper modelMapper) {
        this.storyRepository = storyRepository;
        this.modelMapper = modelMapper;
    }

    public StoryResponseDTO getStoryById(Long id) {
        Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        return modelMapper.map(story, StoryResponseDTO.class);
    }

    public StoryResponseDTO getStoryByAuthor(User author, Long id) {
        //todo check if author is the owner of the story

        Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        if (!Objects.equals(story.getUser().getId(), author.getId())) {
            throw new ResourceNotFoundException("The story does not belong to the author");
        }

        return modelMapper.map(story, StoryResponseDTO.class);
    }

    public void changeContent(Long id, StoryPatchDTO data) {
        Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        story.setText(data.getText());
        storyRepository.save(story);
    }

    public void deleteStory(Long id) {
        storyRepository.deleteById(id);
    }
}
