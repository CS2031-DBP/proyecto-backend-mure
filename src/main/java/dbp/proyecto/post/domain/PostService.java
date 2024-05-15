package dbp.proyecto.post.domain;

import dbp.proyecto.post.dtos.PostMediaDTO;
import dbp.proyecto.post.dtos.PostResponseDTO;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.Song;
import dbp.proyecto.user.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return modelMapper.map(post, PostResponseDTO.class);
    }

    public PostResponseDTO getPostByAuthor(User author, Long id) {
        //todo añadir validacion de autor

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        return modelMapper.map(post, PostResponseDTO.class);
    }

    public void changeMedia(Long id, PostMediaDTO media) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        post.setImageurl(media.getImageurl());
        post.setAudioUrl(media.getAudioUrl());
    }

    public void changeContent(Long id, String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        post.setDescription(content);
    }

    public void changeSong(Long id, Song song) {
        //todo añadir validacion de cancion

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        post.setSong(song);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        postRepository.delete(post);
    }
}
