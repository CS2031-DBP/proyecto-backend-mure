package dbp.proyecto.playlist.application;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.playlist.domain.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Playlist>> getPlaylistsFromUsers(@PathVariable Long userId) {
        List<Playlist> playlists = playlistService.getPlaylistsFromUsers(userId);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<List<Playlist>> getPlaylistsContainingSong(@PathVariable Long songId) {
        List<Playlist> playlists = playlistService.getPlaylistsContainingSong(songId);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Playlist>> getPlaylistsByName(@PathVariable String name) {
        List<Playlist> playlists = playlistService.getPlaylistsByName(name);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        return new ResponseEntity<>(createdPlaylist, HttpStatus.CREATED);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        Playlist playlist = playlistService.removeSongFromPlaylist(playlistId, songId);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        Playlist playlist = playlistService.addSongToPlaylist(playlistId, songId);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

}