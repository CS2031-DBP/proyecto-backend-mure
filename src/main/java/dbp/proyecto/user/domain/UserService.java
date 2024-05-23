package dbp.proyecto.user.domain;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, AuthorizationUtils authorizationUtils, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authorizationUtils = authorizationUtils;
        this.modelMapper = modelMapper;
    }

    public UserBasicInfoResponseDTO getMe() {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserBasicInfoResponseDTO.class);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updatedUser.getProfileImage() != null) {
            existingUser.setProfileImage(updatedUser.getProfileImage());
        }

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        userRepository.save(existingUser);
    }

    // add a new friend
    public void addFriend(Long id, User friend) {
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

    public List<Artist> getFavoriteArtists(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFavoriteArtists();
    }

    public List<Playlist> getOwnsPlaylists(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getOwnsPlaylists();
    }

    public List<Song> getFavoriteSongs(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFavoriteSongs();
    }

    public List<User> getFriends(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFriends();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> findByBirthDateBetween(LocalDate date1, LocalDate date2) {
        return userRepository.findByBirthDateBetween(date1, date2);
    }

    public List<User> findByCreatedAtBefore(LocalDateTime date) {
        return userRepository.findByCreatedAtBefore(date);
    }

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }
}
