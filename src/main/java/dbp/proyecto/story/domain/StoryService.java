package dbp.proyecto.story.domain;

import dbp.proyecto.story.dto.StoryPatchDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Story story = storyRepository.findById(id).orElseThrow(() -> new StoryNotFoundException(id));
        StoryResponseDTO storyResponse = modelMapper.map(story, StoryResponseDTO.class);
        return storyResponse;
    }

    public StoryResponseDTO getStoryByAuthor(User author, Long id) {
        Story story = storyRepository.findById(id).orElseThrow(() -> new StoryNotFoundException(id));
        if (!Objects.equals(story.getUser().getId(), author.getId())) {
            throw new StoryNotFoundException(id);
        }

        StoryResponseDTO storyResponse = modelMapper.map(story, StoryResponseDTO.class);
        return storyResponse;
    }

    public void changeContent(Long id, StoryPatchDTO data) {
        Story story = storyRepository.findById(id).orElseThrow(() -> new StoryNotFoundException(id));
        story.setText(data.getText());
        storyRepository.save(story);
    }

    public void deleteStory(Long id) {
        storyRepository.deleteById(id);
    }
}
