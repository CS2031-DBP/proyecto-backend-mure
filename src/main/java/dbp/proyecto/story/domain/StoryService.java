package dbp.proyecto.story.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.story.dto.StoryBodyDTO;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;

    public StoryService(StoryRepository storyRepository, UserRepository userRepository, ModelMapper modelMapper, SongRepository songRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.songRepository = songRepository;
    }

    public StoryResponseDTO getStoryById(Long id) {
            Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
            StoryResponseDTO storyResponseDTO = modelMapper.map(story, StoryResponseDTO.class);
            storyResponseDTO.setOwner(story.getUser().getName());
            return storyResponseDTO;
    }

    public List<StoryResponseDTO> getStoriesBySongId(Long songId) {
        List<Story> stories = storyRepository.findBySongId(songId);
        return stories.stream().map(story -> {
            StoryResponseDTO storyResponseDTO = modelMapper.map(story, StoryResponseDTO.class);
            storyResponseDTO.setOwner(story.getUser().getName());
            return storyResponseDTO;
        }).collect(Collectors.toList());
    }

    public List<StoryResponseDTO> getStoriesByCreatedAtLessThanEqualAndExpiresAtGreaterThanEqual(LocalDateTime createdAt, LocalDateTime expiresAt) {
        List<Story> stories = storyRepository.findByCreatedAtLessThanEqualAndExpiresAtGreaterThanEqual(createdAt, expiresAt);
        return stories.stream().map(story -> {
            StoryResponseDTO storyResponseDTO = modelMapper.map(story, StoryResponseDTO.class);
            storyResponseDTO.setOwner(story.getUser().getName());
            return storyResponseDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public String createStory(StoryBodyDTO storyBodyDTO) {
        User user = userRepository.findById(storyBodyDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Story story = modelMapper.map(storyBodyDTO, Story.class);
        story.setUser(user);
        if (storyBodyDTO.getSongId() != null) {
            Song song = songRepository.findById(storyBodyDTO.getSongId()).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
            story.setSong(song);
        }
        story.setCreatedAt(LocalDateTime.now());
        story.setExpiresAt(LocalDateTime.now().plusDays(1));
        storyRepository.save(story);
        user.getStories().add(story);
        userRepository.save(user);
        return "/story/" + story.getId();
    }

    @Transactional
    public void deleteStory(Long id) {
        Story story= storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        User user = story.getUser();
        user.getStories().remove(story);
        userRepository.save(user);
        storyRepository.delete(story);
    }
}
