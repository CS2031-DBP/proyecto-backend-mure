package dbp.proyecto.playlist.domain;


import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UniqueResourceAlreadyExist;
import dbp.proyecto.playlist.dtos.PlaylistResponseDTO;
import dbp.proyecto.playlist.infraestructure.PlaylistRepository;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;
import dbp.proyecto.user.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, ModelMapper modelMapper) {
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
    }

    public PlaylistResponseDTO getPlaylistByName(String name) {
        Playlist playlist = playlistRepository.findByName(name);

        if(playlist == null) {
            throw new RuntimeException("Playlist not found");
        }

        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    public PlaylistResponseDTO getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        if (playlist == null) {
            throw new RuntimeException("Playlist not found");
        }

        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    public List<Song> getSongs(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));


        List<PlaylistSongs> songs = playlist.getSongs();

        return songs.stream()
                .map(PlaylistSongs::getSong)
                .collect(Collectors.toList());
    }


    public List<User> getUsers(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));


        List<PlaylistUser> users = playlist.getUsers();

        return users.stream()
                .map(PlaylistUser::getUser)
                .collect(Collectors.toList());

    }

    public void addSong(Song song, Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        boolean songExists = playlist.getSongs().stream()
                .anyMatch(playlistSong -> playlistSong.getSong().equals(song));

        if (songExists) {
            throw new UniqueResourceAlreadyExist("Song already exists in the playlist");
        }

        PlaylistSongs playlistSongs = new PlaylistSongs();
        playlistSongs.setSong(song);
        playlistSongs.setPlaylist(playlist);

        playlist.getSongs().add(playlistSongs);

        playlistRepository.save(playlist);
    }

    public void deleteSong(Song song, Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        boolean songExists = playlist.getSongs().stream()
                .anyMatch(playlistSong -> playlistSong.getSong().equals(song));

        if (!songExists) {
            throw new ResourceNotFoundException("Song already exists in the playlist");
        }

        playlist.getSongs().removeIf(playlistSong -> playlistSong.getSong().equals(song));

        playlistRepository.save(playlist);
    }

    public void addAuthors(User users, Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        boolean userExists = playlist.getUsers().stream()
                .anyMatch(playlistUser -> playlistUser.getUser().equals(users));

        if (userExists) {
            throw new UniqueResourceAlreadyExist("User already exists in the playlist");
        }

        PlaylistUser playlistUser = new PlaylistUser();
        playlistUser.setUser(users);
        playlistUser.setPlaylist(playlist);

        playlist.getUsers().add(playlistUser);

        playlistRepository.save(playlist);
    }

    public void deleteAuthors(User users, Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        boolean userExists = playlist.getUsers().stream()
                .anyMatch(playlistUser -> playlistUser.getUser().equals(users));

        if (!userExists) {
            throw new ResourceNotFoundException("User do not exist in the playlist");
        }

        playlist.getUsers().removeIf(playlistUser -> playlistUser.getUser().equals(users));

        playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        playlistRepository.delete(playlist);
    }

    public void deletePlaylistByName(String name) {
        Playlist playlist = playlistRepository.findByName(name);

        if (playlist == null) {
            throw new ResourceNotFoundException("Playlist not found");
        }

        playlistRepository.delete(playlist);
    }
}
