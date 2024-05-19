package dbp.proyecto.user.applitaction;

import dbp.proyecto.tablasIntermedias.favoriteSong.FavoriteSong;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.story.domain.Story;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.domain.UserService;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/profileImage/{id}")
    public ResponseEntity<Void> updateProfileImage(@PathVariable Long id, @RequestBody String profileImage) {
        userService.updateProfileImage(id, profileImage);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/name/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody String name) {
        userService.updateName(id, name);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/friends/{id}/add")
    public ResponseEntity<Void> updateFriends(@PathVariable Long id, @RequestBody User friend) {
        userService.updateFriendsList(id, friend);
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

    @GetMapping("/favoriteSongs/{id}")
    public ResponseEntity<List<FavoriteSong>> getFavoriteSongs(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteSongs(id));
    }

    @GetMapping("/playlists/{id}")
    public ResponseEntity<List<PlaylistUser>> getPlaylists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getPlaylists(id));
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }


}
