package dbp.proyecto.user.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.dto.AlbumInfoForUserDto;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UnauthorizedOperationException;
import dbp.proyecto.exception.UniqueResourceAlreadyExist;
import dbp.proyecto.media.domain.MediaService;
import dbp.proyecto.playlist.infraestructure.PlaylistRepository;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.dto.SongResponseForUserDto;
import dbp.proyecto.story.infrastructure.StoryRepository;
import dbp.proyecto.user.dto.UserRequestDto;
import dbp.proyecto.user.dto.UserResponseDto;
import dbp.proyecto.user.dto.UserResponseForUserDto;
import dbp.proyecto.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final AuthorizationUtils authorizationUtils;

    private final PlaylistRepository playlistRepository;

    private final PostRepository postRepository;

    private final StoryRepository storyRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final MediaService mediaService;

    private List<UserResponseForUserDto> getUserInfoForUserDTOS(User user) {
        return user.getFriends().stream()
                .map(friend -> {
                    UserResponseForUserDto userInfo = modelMapper.map(friend, UserResponseForUserDto.class);
                    userInfo.setProfileImageUrl(friend.getProfileImageUrl());
                    List<String> friendsNames = friend.getFriends().stream()
                            .map(User::getName)
                            .collect(Collectors.toList());
                    userInfo.setFriendsNames(friendsNames);
                    List<Long> friendsIds = friend.getFriends().stream()
                            .map(User::getId)
                            .collect(Collectors.toList());
                    userInfo.setFriendsIds(friendsIds);
                    return userInfo;
                })
                .collect(Collectors.toList());
    }

    public UserResponseDto getMe() {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
        List<Long> friendsIds = user.getFriends().stream().map(User::getId).collect(Collectors.toList());
        dto.setFriendsIds(friendsIds);
        return dto;
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
        List<Long> friendsIds = user.getFriends().stream().map(User::getId).collect(Collectors.toList());
        dto.setFriendsIds(friendsIds);
        return dto;
    }

    public List<UserResponseDto> getAllUsers() {
        if (!authorizationUtils.isAdmin())
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        return userRepository.findAll().stream()
                .map(user -> {
                    UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
                    List<Long> friendsIds = user.getFriends().stream().map(User::getId).collect(Collectors.toList());
                    dto.setFriendsIds(friendsIds);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserResponseForUserDto> getFriends(Long id) {
        if (!authorizationUtils.isAdmin())
            throw new UnauthorizedOperationException("You are not authorized to perform this action");
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getUserInfoForUserDTOS(user);
    }

    public List<UserResponseForUserDto> getFriendsByCurrentUser() {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getUserInfoForUserDTOS(user);
    }

    @Transactional
    public void updateUser(UserRequestDto updatedUser) throws FileUploadException {
        String email = authorizationUtils.getCurrentUserEmail();
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //print correctly the updated user
        System.out.println("User to update: " + updatedUser.toString());


        MultipartFile profileImage = updatedUser.getProfileImage();
        if (profileImage != null && !profileImage.isEmpty()) {
            String profileImageUrl = mediaService.uploadFile(profileImage);
            existingUser.setProfileImageUrl(profileImageUrl);
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
        if (updatedUser.getNickname() != null && !updatedUser.getNickname().isEmpty()) {
            existingUser.setNickname(updatedUser.getNickname());
        }

        userRepository.save(existingUser);
    }

    @Transactional
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

    @Transactional
    public void removeFriend(Long friendId) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found"));
        if (!user.getFriends().contains(friend)) {
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
        // delete each user that like a post
        List<Post> likedPosts = user.getLikedPosts();
        for (Post post : likedPosts) {
            post.getLikedBy().remove(user);
            post.setLikes(post.getLikes() - 1); // Opcional: Actualizar el contador de "me gusta" si existe
            postRepository.save(post);
        }
        userRepository.delete(user);
    }

    public void updateFavorites(User user, Post post) {
        if (post.getSong() != null && !user.getFavoriteSongs().contains(post.getSong())) {
            user.getFavoriteSongs().add(post.getSong());
        } else if (post.getAlbum() != null && !user.getFavoriteAlbums().contains(post.getAlbum())) {
            user.getFavoriteAlbums().add(post.getAlbum());
        }
        userRepository.save(user);
    }

    public void updateFavoritesDL(User user, Post post) {
        if (post.getSong() != null && user.getFavoriteSongs().contains(post.getSong())) {
            user.getFavoriteSongs().remove(post.getSong());
        } else if (post.getAlbum() != null && user.getFavoriteAlbums().contains(post.getAlbum())) {
            user.getFavoriteAlbums().remove(post.getAlbum());
        }
        userRepository.save(user);
    }

    public List<SongResponseForUserDto> getFavoriteSongs(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getId().equals(currentUser.getId()) && !user.getFriends().contains(currentUser)) {
            throw new UnauthorizedOperationException("Only the owner or a friend can view the favorite songs");
        }
        List<Song> favoriteSongs = user.getFavoriteSongs();
        return favoriteSongs.stream()
                .map(song -> {
                    SongResponseForUserDto dto = modelMapper.map(song, SongResponseForUserDto.class);
                    dto.setArtistNames(song.getArtists().stream()
                            .map(Artist::getName)
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<AlbumInfoForUserDto> getFavoriteAlbums(Long id) {
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
                    AlbumInfoForUserDto dto = modelMapper.map(album, AlbumInfoForUserDto.class);
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
