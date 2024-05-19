package dbp.proyecto.story.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.story.dto.StoryPatchDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.dto.UserInfoForSong;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StoryService {
    private final StoryRepository storyRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public StoryService(StoryRepository storyRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public StoryResponseDTO getStoryById(Long id) {
            Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        return modelMapper.map(story, StoryResponseDTO.class);
    }

    public StoryResponseDTO getStoryByAuthor(UserInfoForSong user, Long id) {
        userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        if (!Objects.equals(story.getUser().getId(), user.getId())) {
            throw new ResourceNotFoundException("The story does not belong to the user");
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
