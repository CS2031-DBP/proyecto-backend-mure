package dbp.proyecto.user.applitaction;

import dbp.proyecto.album.dto.AlbumInfoForUserDto;
import dbp.proyecto.song.dto.SongInfoForUserDto;
import dbp.proyecto.user.domain.UserService;
import dbp.proyecto.user.dto.UserRequestDto;
import dbp.proyecto.user.dto.UserResponseDto;
import dbp.proyecto.user.dto.UserResponseForUserDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/friends/{id}")
    public ResponseEntity<List<UserResponseForUserDto>> getFriends(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/friends/me")
    public ResponseEntity<List<UserResponseForUserDto>> getFriendsByCurrentUser() {
        return ResponseEntity.ok(userService.getFriendsByCurrentUser());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/update/me")
    public ResponseEntity<Void> updateUser(@ModelAttribute UserRequestDto updatedUser) throws FileUploadException {
        userService.updateUser(updatedUser);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/friends/add/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long friendId) {
        userService.addFriend(friendId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/friends/remove/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long friendId) {
        userService.removeFriend(friendId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/favoriteAlbums/{id}")
    public ResponseEntity<List<AlbumInfoForUserDto>> getFavoriteAlbums(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteAlbums(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/favoriteSongs/{id}")
    public ResponseEntity<List<SongInfoForUserDto>> getFavoriteSongs(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getFavoriteSongs(id));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me/friends/{id}")
    public ResponseEntity<Boolean> isFriend(@PathVariable Long id) {
        return ResponseEntity.ok(userService.isFriend(id));
    }
}
