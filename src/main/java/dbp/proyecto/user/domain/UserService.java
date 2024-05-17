package dbp.proyecto.user.domain;

import dbp.proyecto.favoriteSong.FavoriteSong;
import dbp.proyecto.playlistUser.PlaylistUser;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.story.domain.Story;
import dbp.proyecto.story.dto.StoryBodyDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dbp.proyecto.exception.*;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final StoryRepository storyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, PostRepository postRepository, StoryRepository storyRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.modelMapper = modelMapper;
    }

    // DTO
    public UserBasicInfoResponseDTO getUserBasicInfo(Long id) {
        User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(User, UserBasicInfoResponseDTO.class);
    }

    public String saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("User already exist");
        }
        User savedUser = userRepository.save(user);
        return "/user/" + savedUser.getId();

    }

    public List<Post> getPosts(Long id) {
        User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return User.getPosts();
    }

    public String postPost(Long id, PostBodyDTO post) {
    User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Post newPost = modelMapper.map(post, Post.class);
    newPost.setUser(User);

    postRepository.save(newPost);

    return "/post/" + newPost.getId();
    }

    public List<Story> getStories(Long id) {
        User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return User.getStories();
    }

    public String postStory(Long id, StoryBodyDTO story) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Story newStory = modelMapper.map(story, Story.class);
        newStory.setUser(user);

        storyRepository.save(newStory);

        return "/story/" + newStory.getId();
    }


    public List<FavoriteSong> getFavoriteSongs(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFavoriteSongs();
    }

    public List<PlaylistUser> getPlaylists(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getPlaylists();
    }

    public List<User> getFriends(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFriends();
    }

    public void updateProfileImage(Long id, String profileImage) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setProfileImage(profileImage);
        userRepository.save(user);
    }

    public void updateName(Long id, String name) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(name);
        userRepository.save(user);
    }

    public void updatePassword(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(password);
        userRepository.save(user);
    }

    public void updateEmail(Long id, String email) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setEmail(email);
        userRepository.save(user);
    }

    // add a new friend
    public void updateFriendsList(Long id, User friend) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getFriends().add(friend);
        userRepository.save(user);
    }

    // delete a friend
    public void deleteFriend(Long id, User friend) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getFriends().remove(friend);
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
