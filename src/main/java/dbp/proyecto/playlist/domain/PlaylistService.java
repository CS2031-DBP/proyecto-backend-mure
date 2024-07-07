package dbp.proyecto.playlist.domain;


import dbp.proyecto.authentication.utils.AuthorizationUtils;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UnauthorizedOperationException;
import dbp.proyecto.playlist.dtos.PlaylistBodyDTO;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
import dbp.proyecto.playlist.infraestructure.PlaylistRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository, SongRepository songRepository, ModelMapper modelMapper, AuthorizationUtils authorizationUtils) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
        this.authorizationUtils = authorizationUtils;
    }

    private PlaylistResponseDTO getPlaylistResponseDTO(Playlist playlist) {
        PlaylistResponseDTO playlistResponseDTO = modelMapper.map(playlist, PlaylistResponseDTO.class);
        playlistResponseDTO.setUserId(playlist.getUser().getId());
        playlistResponseDTO.setSongsIds(playlist.getSongs().stream()
                .map(Song::getId)
                .collect(Collectors.toList()));
        return playlistResponseDTO;
    }

    public PlaylistResponseDTO getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("User does not have access to this playlist");
        }
        return getPlaylistResponseDTO(playlist);
    }

    public PlaylistResponseDTO getPlaylistByName(String name) {
        Playlist playlist = playlistRepository.findByName(name);
        if (playlist == null) {
            throw new ResourceNotFoundException("Playlist not found");
        }
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User owner = playlist.getUser();

        if (!owner.getId().equals(currentUser.getId()) && !owner.getFriends().contains(currentUser)) {
            throw new UnauthorizedOperationException("User does not have access to this playlist");
        }
        return getPlaylistResponseDTO(playlist);
    }

    public Page<PlaylistResponseDTO> getPlaylistsByUserId(Long userId, Pageable pageable) {
        Page<Playlist> playlistsPage = playlistRepository.findByUserId(userId, pageable);
        return playlistsPage.map(this::getPlaylistResponseDTO);
    }

    public Page<PlaylistResponseDTO> getPlaylistsByCurrentUser(Pageable pageable) {
        String email = authorizationUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Page<Playlist> playlistsPage = playlistRepository.findByUserId(user.getId(), pageable);
        return playlistsPage.map(this::getPlaylistResponseDTO);
    }

    public List<PlaylistResponseDTO> getAllPlaylists() {
        if (!authorizationUtils.isAdmin())
            throw new UnauthorizedOperationException("User does not have access to this playlist");
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream()
                .map(this::getPlaylistResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPlaylists(List<PlaylistBodyDTO> playlistBodyDTOs) {
        for (PlaylistBodyDTO playlistBodyDTO : playlistBodyDTOs) {
            User user = userRepository.findById(playlistBodyDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            List<Song> songs = songRepository.findAllById(playlistBodyDTO.getSongsIds());
            if (songs.size() != playlistBodyDTO.getSongsIds().size()) {
                throw new IllegalArgumentException("Some songs do not exist or are repeated");
            }
            Playlist playlist = new Playlist();
            playlist.setName(playlistBodyDTO.getName());
            playlist.setUser(user);
            playlist.setSongs(songs);
            playlistRepository.save(playlist);
            user.getOwnsPlaylists().add(playlist);
        }
        userRepository.saveAll(playlistBodyDTOs.stream()
                .map(PlaylistBodyDTO::getUserId)
                .distinct()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .collect(Collectors.toList()));
    }

    public boolean isOwner(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        User owner = playlist.getUser();
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return owner.getId().equals(currentUser.getId());
    }

    public void addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        User owner = playlist.getUser();
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!owner.getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("Only the owner can add songs to the playlist");
        }

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if (playlist.getSongs().contains(song)) {
            throw new IllegalArgumentException("The song is already in the playlist");
        }

        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        User owner = playlist.getUser();
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!owner.getId().equals(currentUser.getId())) {
            throw new UnauthorizedOperationException("Only the owner can remove songs from the playlist");
        }

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if (!playlist.getSongs().contains(song)) {
            throw new IllegalArgumentException("The song is not in the playlist");
        }

        playlist.getSongs().remove(song);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void deletePlaylist(Long id) {
        String email = authorizationUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        User owner = playlist.getUser();
        if (!owner.getId().equals(currentUser.getId()) && !authorizationUtils.isAdmin()) {
            throw new UnauthorizedOperationException("Only the owner or an admin can delete the playlist");
        }
        owner.getOwnsPlaylists().remove(playlist);
        userRepository.save(owner);
        playlistRepository.delete(playlist);
    }
}
