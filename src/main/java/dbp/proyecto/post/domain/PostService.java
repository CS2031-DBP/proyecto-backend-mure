package dbp.proyecto.post.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.events.updateFavs.UpdateFavsEvent;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UnauthorizedOperationException;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ApplicationEventPublisher applicationEventPublisher;


    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper, SongRepository songRepository, AlbumRepository albumRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private PostResponseDTO getPostResponseDTO(Post post) {
        PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
        postResponseDTO.setOwner(post.getUser().getName());
        if (post.getSong() != null) {
            postResponseDTO.setSongTitle(post.getSong().getTitle());
        }
        if (post.getAlbum() != null) {
            postResponseDTO.setAlbumTitle(post.getAlbum().getTitle());
        }
        return postResponseDTO;
    }

    public PostResponseDTO getPostById(Long id) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return getPostResponseDTO(post);
    }

    public List<PostResponseDTO> getPostsByUserId(Long userId) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(this::getPostResponseDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByCurrentUser() {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Post> posts = postRepository.findByUserId(user.getId());
        return posts.stream().map(this::getPostResponseDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::getPostResponseDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsBySongId(Long songId) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        List<Post> posts = postRepository.findBySongId(songId);
        return posts.stream().map(this::getPostResponseDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByAlbumId(Long albumId) {
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only an admin can access this post");
        }
        List<Post> posts = postRepository.findByAlbumId(albumId);
        return posts.stream().map(this::getPostResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public void createPosts(List<PostBodyDTO> postBodyDTOs) {
        for (PostBodyDTO postBodyDTO : postBodyDTOs) {
            if (postBodyDTO.getSongId() != null && postBodyDTO.getAlbumId() != null) {
                throw new IllegalArgumentException("A post can have either a song or an album, but not both");
            }
            User user = userRepository.findById(postBodyDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Post post = new Post();
            post.setDescription(postBodyDTO.getDescription());
            if (postBodyDTO.getImageUrl() != null) {
                post.setImageUrl(postBodyDTO.getImageUrl());
            }
            if (postBodyDTO.getAudioUrl() != null) {
                post.setAudioUrl(postBodyDTO.getAudioUrl());
            }
            Random random = new Random();
            post.setUser(user);
            int likes = random.nextInt(201);
            post.setLikes(likes);
            if (postBodyDTO.getSongId() != null) {
                Song song = songRepository.findById(postBodyDTO.getSongId()).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                post.setSong(song);
            }
            if (postBodyDTO.getAlbumId() != null) {
                Album album = albumRepository.findById(postBodyDTO.getAlbumId()).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
                post.setAlbum(album);
            }
            post.setCreatedAt(LocalDateTime.now());
            postRepository.save(post);
            user.getPosts().add(post);
        }
        userRepository.saveAll(postBodyDTOs.stream()
                .map(PostBodyDTO::getUserId)
                .distinct()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .collect(Collectors.toList()));
    }

    public void changeMedia(Long id, PostMediaDTO media) {
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

    public void likePost(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        applicationEventPublisher.publishEvent(new UpdateFavsEvent(user, post));

        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }
}
