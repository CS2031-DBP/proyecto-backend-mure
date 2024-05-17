package dbp.proyecto.user.applitaction;

import dbp.proyecto.favoriteSong.FavoriteSong;
import dbp.proyecto.playlistUser.PlaylistUser;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.dtos.PostBodyDTO;
import dbp.proyecto.story.domain.Story;
import dbp.proyecto.story.dto.StoryBodyDTO;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.domain.UserService;
import dbp.proyecto.user.dto.UserBasicInfoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBasicInfoResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserBasicInfo(id));
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        String uri = userService.saveUser(user);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @PatchMapping("/profileImage/{id}")
    public ResponseEntity<Void> updateProfileImage(@PathVariable Long id, @RequestBody String profileImage) {
        userService.updateProfileImage(id, profileImage);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/name/{id}")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody String name) {
        userService.updateName(id, name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/email/{id}")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody String email) {
        userService.updateEmail(id, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/friends/{id}/add")
    public ResponseEntity<Void> updateFriends(@PathVariable Long id, @RequestBody User friend) {
        userService.updateFriendsList(id, friend);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/friends/{id}/delete")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id, @RequestBody User friend) {
        userService.deleteFriend(id, friend);
        return ResponseEntity.noContent().build();
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

    @PostMapping("/post/{id}")
    public ResponseEntity<Void> savePost(@PathVariable Long id,  @RequestBody PostBodyDTO post) {
        String uri = userService.postPost(id, post);
        return ResponseEntity.created(URI.create(uri)).build();
    }

    @GetMapping("/stories/{id}")
    public ResponseEntity<List<Story>> getStories(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getStories(id));
    }

    @PostMapping("/story/{id}")
    public ResponseEntity<Void> saveStory(@PathVariable Long id,  @RequestBody StoryBodyDTO story) {
        String uri = userService.postStory(id, story);
        return ResponseEntity.created(URI.create(uri)).build();
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
