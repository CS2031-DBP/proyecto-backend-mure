package dbp.proyecto.user.domain;

import dbp.proyecto.favoriteSong.FavoriteSong;
import dbp.proyecto.playlistUser.PlaylistUser;
import dbp.proyecto.post.Post;
import dbp.proyecto.story.Story;
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
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // DTO
    public UserBasicInfoResponseDTO getUserBasicInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserBasicInfoResponseDTO.class);
    }

    public String saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("User already exist");
        }
        User savedUser = userRepository.save(user);
        return "/user/" + savedUser.getId();

    }

    public List<Post> getPosts(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getPosts();
    }

    public List<Story> getStories(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getStories();
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
