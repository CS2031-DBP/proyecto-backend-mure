package dbp.proyecto.playlist.domain;

import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    public List<PlaylistResponseDTO> getPlaylistByName(String name) {
        return null;
    }

    public PlaylistResponseDTO getPlaylistById(Long id) {
        return null;
    }

    public PlaylistResponseDTO getPlaylistByAuthors(User authors) {
        return null;
    }

    public PlaylistResponseDTO getPlaylistBySong(Song song) {
        return null;
    }

    public List<Song> getSongs() {
        return null;
    }

    public List<User> getUsers() {
        return null;
    }

    public void addSong(Song song) {
    }

    public void deleteSong(Song song) {
    }

    public void addAuthors(User authors) {
    }

    public void deleteAuthors(User authors) {
    }

    public void deletePlaylist(Long id) {
    }

    public void deletePlaylistByName(String name) {
    }
}
