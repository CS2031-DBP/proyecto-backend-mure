package dbp.proyecto.story.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.story.dto.StoryBodyDTO;
import dbp.proyecto.story.dto.StoryPatchDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.dto.UserInfoForSong;
import dbp.proyecto.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StoryService {
    private final StoryRepository storyRepository;

    private final UserRepository userRepository;

    private final SongRepository songRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StoryService(StoryRepository storyRepository, UserRepository userRepository, SongRepository songRepository, ModelMapper modelMapper) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
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

    @Transactional
    public String createStory(StoryBodyDTO storyBodyDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Story story = new Story(storyBodyDTO.getVideoUrl(), storyBodyDTO.getText(), user, storyBodyDTO.getSong());

        if (storyBodyDTO.getSong() != null) {
            Song song = songRepository.findById(storyBodyDTO.getSong().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
            story.setSong(song);
        }

        storyRepository.save(story);
        user.getStories().add(story);
        userRepository.save(user);

        return "/story/" + story.getId();
    }

}
