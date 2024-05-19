package dbp.proyecto.user.domain;

import dbp.proyecto.tablasIntermedias.favoriteSong.FavoriteSong;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.story.domain.Story;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public List<Post> getPosts(Long id) {
        User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return User.getPosts();
    }

    public List<Story> getStories(Long id) {
        User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return User.getStories();
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

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }

}
