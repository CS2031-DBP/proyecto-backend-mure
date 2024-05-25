package dbp.proyecto.user.applitaction;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.post.dtos.PostResponseDTO;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.story.domain.Story;
import dbp.proyecto.story.dto.StoryResponseDTO;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.domain.UserService;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import dbp.proyecto.user.dto.UserBodyDTO;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me") // ✔️
    public ResponseEntity<UserBasicInfoResponseDTO> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}") // ✔️
    public ResponseEntity<UserBasicInfoResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all") // ✔️
    public ResponseEntity<List<UserBasicInfoResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/friends/{id}") // ✔️
    public ResponseEntity<List<UserBasicInfoResponseDTO>> getFriends(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }

    // todo get friends me

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}") // ✔️
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserBodyDTO updatedUser) {
        userService.updateUser(id, updatedUser);
        return ResponseEntity.noContent().build();
    }

    // todo patch mapping para user me

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/friends/{id}/add/{friendId}") // ✔️
    public ResponseEntity<Void> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
        return ResponseEntity.noContent().build();
    }

    // todo patch mapping para user me

    @PatchMapping("/friends/{id}/delete/{friendId}") // ✔️
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
        return ResponseEntity.noContent().build();
    }

    // todo patch mapping para user me


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}") // ✔️
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // todo delete user me


    /*
    @GetMapping("/artists/{id}")
    public ResponseEntity<List<Artist>> getFavoriteArtists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteArtists(id));
    }
    

    @GetMapping("/songs/{id}")
    public ResponseEntity<List<Song>> getFavoriteSongs(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteSongs(id));
    }

     */



}
