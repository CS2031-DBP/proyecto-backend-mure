package dbp.proyecto.post.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.dto.AlbumInfoForPostDto;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UnauthorizedOperationException;
import dbp.proyecto.mediaStorage.domain.MediaStorageService;
import dbp.proyecto.notification.domain.NotificationService;
import dbp.proyecto.post.dtos.PostRequestDto;
import dbp.proyecto.post.dtos.PostResponseDto;
import dbp.proyecto.post.dtos.PostUpdateDto;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.dto.SongResponseForPostDto;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final SongRepository songRepository;

    private final AlbumRepository albumRepository;

    private final AuthorizationUtils authorizationUtils;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final MediaStorageService mediaStorageService;

    private final NotificationService notificationService;

    private PostResponseDto getPostResponseDto(Post post) {
        PostResponseDto postResponseDTO = modelMapper.map(post, PostResponseDto.class);
        postResponseDTO.setOwner(post.getUser().getNickname());
        postResponseDTO.setOwnerId(post.getUser().getId());
        postResponseDTO.setProfileImage(post.getUser().getProfileImageUrl());
        postResponseDTO.setLikedByUserIds(post.getLikedBy().stream().map(User::getId).collect(Collectors.toSet()));
        postResponseDTO.setCreatedAt(post.getCreatedAt());

        if (post.getSong() != null) {
            SongResponseForPostDto songDto = new SongResponseForPostDto();
            songDto.setId(post.getSong().getId());
            songDto.setTitle(post.getSong().getTitle());
            songDto.setSpotifyUrl(post.getSong().getSpotifyUrl());
            songDto.setCoverImageUrl(post.getSong().getCoverImageUrl());
            List<String> artistNames = post.getSong().getArtists().stream()
                    .map(Artist::getName)
                    .collect(Collectors.toList());
            songDto.setArtistsNames(artistNames);
            songDto.setDuration(post.getSong().getDuration());
            songDto.setGenre(post.getSong().getGenre());
            songDto.setSpotifyPreviewUrl(post.getSong().getSpotifyPreviewUrl());
            postResponseDTO.setSong(songDto);
        }

        if (post.getAlbum() != null) {
            AlbumInfoForPostDto albumDto = new AlbumInfoForPostDto();
            albumDto.setId(post.getAlbum().getId());
            albumDto.setTitle(post.getAlbum().getTitle());
            albumDto.setSpotifyUrl(post.getAlbum().getSpotifyUrl());
            albumDto.setCoverImageUrl(post.getAlbum().getCoverImageUrl());
            albumDto.setArtist(post.getAlbum().getArtist().getName());
            albumDto.setDuration(post.getAlbum().getTotalDuration());
            albumDto.setSongs(post.getAlbum().getSongs().stream().map(Song::getTitle).collect(Collectors.toList()));
            postResponseDTO.setAlbum(albumDto);
        }
        return postResponseDTO;
    }

    public PostResponseDto getPostById(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return getPostResponseDto(post);
    }

    public Page<PostResponseDto> getPostsByUserId(Long userId, Pageable pageable) {
        Page<Post> posts = postRepository.findByUserId(userId, pageable);
        return posts.map(this::getPostResponseDto);
    }

    public Page<PostResponseDto> getPostsByCurrentUser(int page, int size) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(user.getId(), pageable);
        return posts.map(this::getPostResponseDto);
    }

    public Page<PostResponseDto> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return posts.map(this::getPostResponseDto);
    }

    public List<PostResponseDto> getPostsBySongId(Long songId) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        List<Post> posts = postRepository.findBySongId(songId);
        return posts.stream().map(this::getPostResponseDto).collect(Collectors.toList());
    }

    public List<PostResponseDto> getPostsByAlbumId(Long albumId) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        List<Post> posts = postRepository.findByAlbumId(albumId);
        return posts.stream().map(this::getPostResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public void createPost(PostRequestDto postRequestDto) throws FileUploadException {
        if (postRequestDto.getSongId() != null && postRequestDto.getAlbumId() != null) {
            throw new IllegalArgumentException("A post can have either a song or an album, but not both");
        }

        User user = userRepository.findById(postRequestDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = new Post();

        post.setDescription(postRequestDto.getDescription());

        if (postRequestDto.getImage() != null && !postRequestDto.getImage().isEmpty()) {
            post.setImageUrl(mediaStorageService.uploadFile(postRequestDto.getImage()));
        }

        if (postRequestDto.getAudio() != null && !postRequestDto.getAudio().isEmpty()) {
            post.setAudioUrl(mediaStorageService.uploadFile(postRequestDto.getAudio()));
        }

        Random random = new Random();
        post.setUser(user);
        int likes = random.nextInt(201);
        post.setLikes(likes);

        if (postRequestDto.getSongId() != null) {
            Song song = songRepository.findById(postRequestDto.getSongId()).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
            post.setSong(song);
        }

        if (postRequestDto.getAlbumId() != null) {
            Album album = albumRepository.findById(postRequestDto.getAlbumId()).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
            post.setAlbum(album);
        }

        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        user.getPosts().add(post);
        userRepository.save(user);

        List<User> friends = user.getFriends();
        for (User friend : friends) {
            String expoPushToken = friend.getExpoPushToken();
            if (expoPushToken != null) {
                if (post.getAlbum() !=  null) {
                    notificationService.sendNotification(expoPushToken, "New Post at " + post.getCreatedAt(), user.getNickname() + " has created a new post about the album " + post.getAlbum().getTitle());
                } else if (post.getSong() != null) {
                    notificationService.sendNotification(expoPushToken, "New Post at " + post.getCreatedAt(), user.getNickname() + " has created a new post about the song " + post.getSong().getTitle());
                }
            }
        }

    }

    @Transactional
    public void createPosts(List<PostRequestDto> postRequestDtos) throws FileUploadException {
        for (PostRequestDto postRequestDto : postRequestDtos) {
            createPost(postRequestDto);
        }

        userRepository.saveAll(postRequestDtos.stream()
                .map(PostRequestDto::getUserId)
                .distinct()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .collect(Collectors.toList()));
    }

    public void changeMedia(Long id, PostUpdateDto media) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!authorizationUtils.isAdminOrResourceOwner(user.getId())) {
            throw new UnauthorizedOperationException("Only the owner can change the media of this post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (media.getImageUrl() != null) {
            post.setImageUrl(media.getImageUrl());
        }
        if (media.getAudioUrl() != null) {
            post.setAudioUrl(media.getAudioUrl());
        }
        if (media.getDescription() != null) {
            post.setDescription(media.getDescription());
        }
        postRepository.save(post);
    }

    public void changeContent(Long id, Long songId, Long albumId) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!authorizationUtils.isAdminOrResourceOwner(user.getId())) {
            throw new UnauthorizedOperationException("Only the owner can change the content of this post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (songId != null) {
            Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
            post.setSong(song);
        }
        if (albumId != null) {
            Album album = albumRepository.findById(albumId).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
            post.setAlbum(album);
        }
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!authorizationUtils.isAdminOrResourceOwner(currentUser.getId())) {
            throw new UnauthorizedOperationException("Only the owner can delete this post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = post.getUser();
        user.getPosts().remove(post);
        userRepository.save(user);
        postRepository.delete(post);
    }

    @Transactional
    public void likePost(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getLikedBy().contains(user)) {
            post.getLikedBy().add(user);
            user.getLikedPosts().add(post); // Asegura la bidireccionalidad
            post.setLikes(post.getLikes() + 1);
            postRepository.save(post);
            userRepository.save(user); // Guarda también el usuario
        }
    }

    @Transactional
    public void dislikePost(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user);
            user.getLikedPosts().remove(post); // Asegura la bidireccionalidad
            post.setLikes(post.getLikes() - 1);
            postRepository.save(post);
            userRepository.save(user); // Guarda también el usuario
        }
    }

}
