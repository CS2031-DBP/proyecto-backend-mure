package dbp.proyecto.user.applitaction;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.UserService;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import dbp.proyecto.user.dto.UserBodyDTO;
import dbp.proyecto.user.dto.UserInfoForUserDTO;
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
    public ResponseEntity<List<UserInfoForUserDTO>> getFriends(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/friends/me") // ✔️
    public ResponseEntity<List<UserInfoForUserDTO>> getFriendsByCurrentUser() {
        return ResponseEntity.ok(userService.getFriendsByCurrentUser());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/update/me") // ✔️
    public ResponseEntity<Void> updateUser(@RequestBody UserBodyDTO updatedUser) {
        userService.updateUser(updatedUser);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/friends/add/{friendId}") // ✔️
    public ResponseEntity<Void> addFriend(@PathVariable Long friendId) {
        userService.addFriend(friendId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/friends/remove/{friendId}") // ✔️
    public ResponseEntity<Void> removeFriend(@PathVariable Long friendId) {
        userService.removeFriend(friendId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}") // ✔️
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/addFavoriteSong/{songId}") // ✔️
    public ResponseEntity<Void> addFavoriteSong(@PathVariable Long songId) {
        userService.addFavoriteSong(songId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/removeFavoriteSong/{songId}") // ✔️
    public ResponseEntity<Void> removeFavoriteSong(@PathVariable Long songId) {
        userService.removeFavoriteSong(songId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/addFavoriteArtist/{artistId}") // ✔️
    public ResponseEntity<Void> addFavoriteArtist(@PathVariable Long artistId) {
        userService.addFavoriteArtist(artistId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/removeFavoriteArtist/{artistId}") // ✔️
    public ResponseEntity<Void> removeFavoriteArtist(@PathVariable Long artistId) {
        userService.removeFavoriteArtist(artistId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/favoriteArtists/{id}")
    public ResponseEntity<List<Artist>> getFavoriteArtists(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteArtists(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/favoriteSongs/{id}")
    public ResponseEntity<List<Song>> getFavoriteSongs(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteSongs(id));
    }

}
