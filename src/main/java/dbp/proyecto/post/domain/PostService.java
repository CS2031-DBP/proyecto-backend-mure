package dbp.proyecto.post.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.dto.UserInfoForSong;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return modelMapper.map(post, PostResponseDTO.class);
    }

    public PostResponseDTO getPostByAuthor(UserInfoForSong author, Long id) {
        userRepository.findById(author.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return modelMapper.map(post, PostResponseDTO.class);
    }

    public void changeMedia(Long id, PostMediaDTO media) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setImageurl(media.getImageurl());
        post.setAudioUrl(media.getAudioUrl());
    }

    public void changeContent(Long id, String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setDescription(content);
    }

    public void changeSong(Long id, Song song) {
        //todo añadir validacion de cancion

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setSong(song);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        postRepository.delete(post);
    }
}
