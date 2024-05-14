package dbp.proyecto.post.domain;

import dbp.proyecto.post.dtos.PostResponseDTO;
import dbp.proyecto.song.Song;
import dbp.proyecto.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    public List<PostResponseDTO> getPostBySong(Song song) {
    }

    public PostResponseDTO getPostByAuthor(User author) {
    }

    public void changeMedia(Long id, String media) {
    }

    public void changeContent(Long id, String content) {
    }

    public void changeSong(Long id, Song song) {
    }

    public void deletePost(Long id) {
    }

    public PostResponseDTO getPostById(Long id) {
    }
}
