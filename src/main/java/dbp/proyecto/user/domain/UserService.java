package dbp.proyecto.user.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.dto.AlbumInfoForUserDTO;
import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.playlist.infraestructure.PlaylistRepository;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.dto.SongInfoForUserDTO;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import dbp.proyecto.user.dto.UserBodyDTO;
import dbp.proyecto.user.dto.UserInfoForUserDTO;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dbp.proyecto.exception.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorizationUtils authorizationUtils;
    private final PlaylistRepository playlistRepository;
    private final PostRepository postRepository;
    private final StoryRepository storyRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorizationUtils authorizationUtils, PlaylistRepository playlistRepository, PostRepository postRepository, StoryRepository storyRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorizationUtils = authorizationUtils;
        this.playlistRepository = playlistRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    private List<UserInfoForUserDTO> getUserInfoForUserDTOS(User user) {
        return user.getFriends().stream()
                .map(friend -> {
                    UserInfoForUserDTO userInfo = modelMapper.map(friend, UserInfoForUserDTO.class);
                    List<String> friendsNames = friend.getFriends().stream()
                            .map(User::getName)
                            .collect(Collectors.toList());
                    userInfo.setFriendsNames(friendsNames);
                    return userInfo;
                })
                .collect(Collectors.toList());
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
        if (!authorizationUtils.isAdmin())
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserBasicInfoResponseDTO.class))
                .toList();
    }

    public List<UserInfoForUserDTO> getFriends(Long id) {
        if (!authorizationUtils.isAdmin())
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getUserInfoForUserDTOS(user);
    }

    public List<UserInfoForUserDTO> getFriendsByCurrentUser(){
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getUserInfoForUserDTOS(user);
    }

    public void updateUser(UserBodyDTO updatedUser) {
        String email = authorizationUtils.getCurrentUserEmail();
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updatedUser.getProfileImage() != null && !updatedUser.getProfileImage().isEmpty()) {
            existingUser.setProfileImage(updatedUser.getProfileImage());
        }
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            existingUser.setPassword(encodedPassword);
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        userRepository.save(existingUser);
    }


    public void addFriend(Long friendId) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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

    public void removeFriend(Long friendId) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        playlistRepository.deleteAll(user.getOwnsPlaylists());
        postRepository.deleteAll(user.getPosts());
        storyRepository.deleteAll(user.getStories());
        for (User friend : user.getFriends()) {
            friend.getFriends().remove(user);
            userRepository.save(friend);
        }
        userRepository.delete(user);
    }

    public void updateFavorites(User user, Post post) {
        if(post.getSong() != null && !user.getFavoriteSongs().contains(post.getSong())){
            user.getFavoriteSongs().add(post.getSong());
        }
        else if(post.getAlbum() != null && !user.getFavoriteAlbums().contains(post.getAlbum())){
            user.getFavoriteAlbums().add(post.getAlbum());
        }
        userRepository.save(user);
    }

    public void updateFavoritesDL(User user, Post post) {
        if(post.getSong() != null && user.getFavoriteSongs().contains(post.getSong())){
            user.getFavoriteSongs().remove(post.getSong());
        }
        else if(post.getAlbum() != null && user.getFavoriteAlbums().contains(post.getAlbum())){
            user.getFavoriteAlbums().remove(post.getAlbum());
        }
        userRepository.save(user);
    }

public Set<SongInfoForUserDTO> getFavoriteSongs(Long id) {
    String email = authorizationUtils.getCurrentUserEmail();
    User currentUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    if (!user.getId().equals(currentUser.getId()) && !user.getFriends().contains(currentUser)) {
        throw new UnauthorizedOperationException("Only the owner or a friend can view the favorite songs");
    }
    Set<Song> favoriteSongs = user.getFavoriteSongs();
    return favoriteSongs.stream()
            .map(song -> {
                SongInfoForUserDTO dto = modelMapper.map(song, SongInfoForUserDTO.class);
                dto.setArtistNames(song.getArtists().stream()
                        .map(Artist::getName)
                        .collect(Collectors.toSet()));
                return dto;
            })
            .collect(Collectors.toSet());
}

    public List<AlbumInfoForUserDTO> getFavoriteAlbums(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getId().equals(currentUser.getId()) && !user.getFriends().contains(currentUser)) {
            throw new UnauthorizedOperationException("Only the owner or a friend can view the favorite albums");
        }
        List<Album> favoriteAlbums = user.getFavoriteAlbums();
        return favoriteAlbums.stream()
                .map(album -> {
                    AlbumInfoForUserDTO dto = modelMapper.map(album, AlbumInfoForUserDTO.class);
                    dto.setArtistName(album.getArtist().getName());
                    return dto;
                })
                .collect(Collectors.toList());
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

    public Boolean isFriend(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (currentUser.getId().equals(id)) {
            throw new IllegalArgumentException("The provided ID is the same as the current user's ID");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFriends().contains(currentUser);
    }
}
