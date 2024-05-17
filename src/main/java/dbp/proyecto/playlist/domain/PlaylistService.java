package dbp.proyecto.playlist.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.playlist.dto.PlaylistDTO;
import dbp.proyecto.playlist.infrastructure.PlaylistRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongsRepository;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUserRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    private final PlaylistRepository repository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final PlaylistSongsRepository playlistSongsRepository;
    private final PlaylistUserRepository playlistUserRepository;

    @Autowired
    public PlaylistService(PlaylistRepository repository, ModelMapper mapper, SongRepository songRepository, PlaylistSongsRepository playlistSongsRepository, UserRepository userRepository, PlaylistUserRepository playlistUserRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.songRepository = songRepository;
        this.playlistSongsRepository = playlistSongsRepository;
        this.userRepository = userRepository;
        this.playlistUserRepository = playlistUserRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaylistDTO> getPlaylistsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getPlaylists().stream()
                .map(playlistUser -> mapper.map(playlistUser.getPlaylist(), PlaylistDTO.class))
                .collect(Collectors.toList());
    }

/*    @Transactional(readOnly = true)
    public List<PlaylistDTO> getPlaylistsBySong(String songTitle) {
        return repository.findBySongsTitle(songTitle).stream()
                .map(playlist -> mapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
    }*/

    @Transactional(readOnly = true)
    public List<PlaylistDTO> getPlaylistsByName(String name) {
        return repository.findByName(name).stream()
                .map(playlist -> mapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPlaylist(PlaylistDTO playlistDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Playlist playlist = mapper.map(playlistDTO, Playlist.class);
        PlaylistUser playlistUser = new PlaylistUser(playlist, user);
        playlist.getAuthors().add(playlistUser);
        repository.save(playlist);
        playlistUserRepository.save(playlistUser); // Save the relationship
    }

    @Transactional
    public void addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        PlaylistSongs playlistSongs = new PlaylistSongs(playlist, song);
        playlist.getSongs().add(playlistSongs);
        playlistSongsRepository.save(playlistSongs);
    }

    @Transactional
    public void deleteSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        PlaylistSongs playlistSongs = new PlaylistSongs(playlist, song);
        playlist.getSongs().remove(playlistSongs);
        playlistSongsRepository.delete(playlistSongs);
    }

    @Transactional
    public void addAuthorToPlaylist(Long playlistId, Long userId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        PlaylistUser playlistUser = new PlaylistUser(playlist, user);
        playlist.getAuthors().add(playlistUser);
        playlistUserRepository.save(playlistUser);
    }

    @Transactional
    public void deleteAuthorFromPlaylist(Long playlistId, Long userId) {
        Playlist playlist = repository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        PlaylistUser playlistUser = new PlaylistUser(playlist, user);
        playlist.getAuthors().remove(playlistUser);
        playlistUserRepository.delete(playlistUser);
    }

    @Transactional
    public void deletePlaylist(Long playlistId) {
        repository.deleteById(playlistId);
    }
}
