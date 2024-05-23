package dbp.proyecto.user.applitaction;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.story.domain.Story;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.domain.UserService;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<UserBasicInfoResponseDTO> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/friends/{id}/add")
    public ResponseEntity<Void> addFriend(@PathVariable Long id, @RequestBody User friend) {
        userService.addFriend(id, friend);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/friends/{id}/delete")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id, @RequestBody User friend) {
        userService.deleteFriend(id, friend);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getPosts(id));
    }

    @GetMapping("/stories/{id}")
    public ResponseEntity<List<Story>> getStories(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getStories(id));
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<List<Artist>> getFavoriteArtists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteArtists(id));
    }

    @GetMapping("/playlists/{id}")
    public ResponseEntity<List<Playlist>> getOwnsPlaylists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getOwnsPlaylists(id));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<List<Song>> getFavoriteSongs(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteSongs(id));
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }

    @GetMapping("/birthDateBetween")
    public ResponseEntity<List<User>> findByBirthDateBetween(@RequestParam LocalDate date1, @RequestParam LocalDate date2) {
        List<User> users = userService.findByBirthDateBetween(date1, date2);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/createdBefore")
    public ResponseEntity<List<User>> findByCreatedAtBefore(@RequestParam LocalDateTime date) {
        List<User> users = userService.findByCreatedAtBefore(date);
        return ResponseEntity.ok(users);
    }

}
