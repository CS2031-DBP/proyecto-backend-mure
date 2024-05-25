package dbp.proyecto.user.domain;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.playlist.infraestructure.PlaylistRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import dbp.proyecto.user.dto.UserBodyDTO;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dbp.proyecto.exception.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationUtils authorizationUtils;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, AuthorizationUtils authorizationUtils, PlaylistRepository playlistRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authorizationUtils = authorizationUtils;
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
    }

    public UserBasicInfoResponseDTO getMe() {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserBasicInfoResponseDTO.class);
    }

    public UserBasicInfoResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserBasicInfoResponseDTO.class);
    }

    public List<UserBasicInfoResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserBasicInfoResponseDTO.class))
                .toList();
    }

    public void updateUser(Long id, UserBodyDTO updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updatedUser.getProfileImage() != null) {
            existingUser.setProfileImage(updatedUser.getProfileImage());
        }

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getPassword() != null) { // hay que decodificar la contraseña nuevamente
            existingUser.setPassword(updatedUser.getPassword());
        }

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        userRepository.save(existingUser);
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found"));

        if (user.getFriends().contains(friend)) {
            throw new UniqueResourceAlreadyExist("User is already a friend");
        }

        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userRepository.save(user);
        userRepository.save(friend);
    }

    public void deleteFriend(Long id, Long friendId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found"));

        if(!user.getFriends().contains(friend)){
            throw new ResourceNotFoundException("Friend not found");
        }

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        for (Playlist playlist : user.getOwnsPlaylists()) {
            if (playlist.getUsers().get(0).getId().equals(user.getId())) {
                playlistRepository.delete(playlist);
            } else {
                playlist.getUsers().remove(user);
                playlistRepository.save(playlist);
            }
        }
        for (User friend : user.getFriends()) {
            friend.getFriends().remove(user);
            userRepository.save(friend);
        }
        userRepository.delete(user);
    }

    public List<Artist> getFavoriteArtists(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFavoriteArtists();
    }

    public List<Song> getFavoriteSongs(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFavoriteSongs();
    }

    public List<UserBasicInfoResponseDTO> getFriends(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFriends().stream()
                .map(friend -> modelMapper.map(friend, UserBasicInfoResponseDTO.class))
                .toList();
    }

    public List<UserBasicInfoResponseDTO> findByBirthDateBetween(LocalDate date1, LocalDate date2) {
        List<User> users = userRepository.findByBirthDateBetween(date1, date2);
        return users.stream()
                .map(user -> modelMapper.map(user, UserBasicInfoResponseDTO.class))
                .toList();
    }

    public List<UserBasicInfoResponseDTO> findByCreatedAtBefore(LocalDateTime date) {
        List<User> users = userRepository.findByCreatedAtBefore(date);
        return users.stream()
                .map(user -> modelMapper.map(user, UserBasicInfoResponseDTO.class))
                .toList();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
