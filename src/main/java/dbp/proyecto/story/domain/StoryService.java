package dbp.proyecto.story.domain;

import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UnauthorizedOperationException;
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
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;
    private final AuthorizationUtils authorizationUtils;

    public StoryService(StoryRepository storyRepository, UserRepository userRepository, ModelMapper modelMapper, SongRepository songRepository, AuthorizationUtils authorizationUtils) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.songRepository = songRepository;
        this.authorizationUtils = authorizationUtils;
    }

    private StoryResponseDTO getStoryResponseDTO(Story story) {
        StoryResponseDTO storyResponseDTO = modelMapper.map(story, StoryResponseDTO.class);
        storyResponseDTO.setOwner(story.getUser().getName());
        if (story.getSong() != null) {
            storyResponseDTO.setSongTitle(story.getSong().getTitle());
        }
        return storyResponseDTO;
    }

    public StoryResponseDTO getStoryById(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        }
        Story story = storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        return getStoryResponseDTO(story);
    }

    public List<StoryResponseDTO> getStoriesByUserId(Long userId) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        }
        List<Story> stories = storyRepository.findByUserId(userId);
        return stories.stream().map(this::getStoryResponseDTO).collect(Collectors.toList());
    }

    public List<StoryResponseDTO> getStoriesByCurrentUser() {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Story> stories = storyRepository.findByUserId(user.getId());
        return stories.stream().map(this::getStoryResponseDTO).collect(Collectors.toList());
    }

    public List<StoryResponseDTO> getAllStories(){
        List<Story> stories = storyRepository.findAll();
        return stories.stream().map(this::getStoryResponseDTO).collect(Collectors.toList());
    }

    public List<StoryResponseDTO> getStoriesBySongId(Long songId) {
        if (!authorizationUtils.isAdmin()) {
            throw new ResourceNotFoundException("Unauthorized");
        }
        List<Story> stories = storyRepository.findBySongId(songId);
        return stories.stream().map(this::getStoryResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void createStories(List<StoryBodyDTO> storyBodyDTOs) {
        for (StoryBodyDTO storyBodyDTO : storyBodyDTOs) {
            User user = userRepository.findById(storyBodyDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Story story = new Story();
            story.setVideoUrl(storyBodyDTO.getVideoUrl());
            if (storyBodyDTO.getText() != null) {
                story.setText(storyBodyDTO.getText());
            }
            Random random = new Random();
            story.setUser(user);
            int likes = random.nextInt(301);
            story.setLikes(likes);
            if (storyBodyDTO.getSongId() != null) {
                Song song = songRepository.findById(storyBodyDTO.getSongId()).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                story.setSong(song);
            }
            story.setCreatedAt(LocalDateTime.now());
            story.setExpiresAt(LocalDateTime.now().plusDays(1));
            storyRepository.save(story);
            user.getStories().add(story);
        }
        userRepository.saveAll(storyBodyDTOs.stream().map(StoryBodyDTO::getUserId).distinct().map(userId -> userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"))).collect(Collectors.toList()));
    }

    @Transactional
    public void deleteStory(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!authorizationUtils.isAdminOrResourceOwner(currentUser.getId())) {
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        }
        Story story= storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        User user = story.getUser();
        user.getStories().remove(story);
        userRepository.save(user);
        storyRepository.delete(story);
    }
}
