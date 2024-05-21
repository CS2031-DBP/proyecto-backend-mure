package dbp.proyecto.post.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.playlist.infraestructure.PlaylistRepository;
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

import java.sql.Timestamp;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SongRepository songRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper, SongRepository songRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.songRepository = songRepository;
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return modelMapper.map(post, PostResponseDTO.class);
    }

    @Transactional
    public String createPost(PostBodyDTO postBodyDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = new Post(postBodyDTO.getDescription(), postBodyDTO.getImageUrl(), postBodyDTO.getAudioUrl(), user, postBodyDTO.getSong(), postBodyDTO.getPlaylist());

        System.out.println(post.getDate());

        if (postBodyDTO.getSong() != null) {
            Song song = songRepository.findById(postBodyDTO.getSong().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
            post.setSong(song);
        }

        postRepository.save(post);
        user.getPosts().add(post);
        userRepository.save(user);

        return "/post/" + post.getId();
    }



    public void changeMedia(Long id, PostMediaDTO media) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setImageUrl(media.getImageUrl());
        post.setAudioUrl(media.getAudioUrl());
    }

    public void changeDescription(Long id, String description) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setDescription(description);
    }

    public void changeSong(Long id, Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setSong(song);
    }

/*    public void changePlaylist(Long id, Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setPlaylist(playlist);
    }*/

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = post.getUser();
        user.getPosts().remove(post);
        userRepository.save(user);
        postRepository.delete(post);
    }
}
