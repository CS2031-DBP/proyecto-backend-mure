package dbp.proyecto.playlist.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UniqueResourceAlreadyExist;
import dbp.proyecto.playlist.infrastructure.PlaylistRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository repository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final ModelMapper mapper;

    @Autowired
    public PlaylistService(PlaylistRepository repository, ModelMapper mapper, UserRepository userRepository, SongRepository songRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.songRepository=songRepository;
        this.userRepository = userRepository;
    }

    public List<Playlist> getPlaylistsFromUsers(Long userId) {
        List<Playlist> playlists = repository.findByUsersId(userId);
        if (playlists.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
        return playlists;
    }

    public List<Playlist> getPlaylistsContainingSong(Long songId) {
        List<Playlist> playlists = repository.findBySongId(songId);
        if (playlists.isEmpty()) {
            throw new ResourceNotFoundException("Song with ID " + songId + " not found");
        }
        return playlists;
    }

    public List<Playlist> getPlaylistsByName(String name) {
        List<Playlist> playlists = repository.findByNameContaining(name);
        if (playlists.isEmpty()) {
            throw new ResourceNotFoundException("Playlist with name " + name + " not found");
        }
        return playlists;
    }

    public Playlist createPlaylist(Playlist playlist) {
        Optional<Playlist> existingPlaylist = repository.findByName(playlist.getName());
        if (existingPlaylist.isPresent()) {
            throw new UniqueResourceAlreadyExist("Playlist with name " + playlist.getName() + " already exists");
        }
        return repository.save(playlist);
    }

    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with ID " + playlistId + " not found"));
        playlist.getSongs().removeIf(song -> song.getId().equals(songId));
        return repository.save(playlist);
    }


    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with ID " + playlistId + " not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID " + songId + " not found"));
        PlaylistSongs playlistSongs = new PlaylistSongs();
        playlistSongs.setPlaylist(playlist);
        playlistSongs.setSong(song);
        playlist.getSongs().add(playlistSongs);
        return repository.save(playlist);
    }

    public User deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));
        user.setActive(false);
        return userRepository.save(user);
    }
    public void deletePlaylist(Long playlistId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist with ID " + playlistId + " not found"));
        repository.delete(playlist);
    }
}

