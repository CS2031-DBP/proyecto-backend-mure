package dbp.proyecto.post.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper, SongRepository songRepository, AlbumRepository albumRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
        postResponseDTO.setOwner(post.getUser().getName());
        return postResponseDTO;
    }

    public List<PostResponseDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(post -> {
            PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
            postResponseDTO.setOwner(post.getUser().getName());
            return postResponseDTO;
        }).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> {
            PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
            postResponseDTO.setOwner(post.getUser().getName());
            return postResponseDTO;
        }).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsBySongId(Long songId) {
        List<Post> posts = postRepository.findBySongId(songId);
        return posts.stream().map(post -> {
            PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
            postResponseDTO.setOwner(post.getUser().getName());
            return postResponseDTO;
        }).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByAlbumId(Long albumId) {
        List<Post> posts = postRepository.findByAlbumId(albumId);
        return posts.stream().map(post -> {
            PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
            postResponseDTO.setOwner(post.getUser().getName());
            return postResponseDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public String createPost(PostBodyDTO postBodyDTO) {
        User user = userRepository.findById(postBodyDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = modelMapper.map(postBodyDTO, Post.class);
        post.setUser(user);
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
        userRepository.save(user);
        return "/post/" + post.getId();
    }

    public void changeMedia(Long id, PostMediaDTO media) {
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
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = post.getUser();
        user.getPosts().remove(post);
        userRepository.save(user);
        postRepository.delete(post);
    }
}
