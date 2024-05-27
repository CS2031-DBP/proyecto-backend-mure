package dbp.proyecto.album.controller;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.domain.Song;
import java.util.List;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.utils.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ROLE_ADMIN")
class AlbumControllerTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    Reader reader;

    @BeforeEach
    @WithMockUser(roles = "ADMIN")
    public void setUp() throws Exception {
        albumRepository.deleteAll();
        songRepository.deleteAll();
        artistRepository.deleteAll();

        mockMvc.perform(post("/auth/signin")
                        .contentType(APPLICATION_JSON)
                        .content(Reader.readJsonFile("/user/post.json")))
                .andReturn();

        String artistJson = Reader.readJsonFile("/artist/post.json");
        System.out.println("Artist JSON: " + artistJson);
        MvcResult artistResult = mockMvc.perform(post("/artist")
                        .contentType(APPLICATION_JSON)
                        .content(artistJson))
                .andReturn();
        System.out.println("Artist creation result: " + artistResult.getResponse().getContentAsString());

        List<Artist> artists = artistRepository.findAll();
        System.out.println("Artists created: " + artists);

        MvcResult songResult = mockMvc.perform(post("/song")
                        .contentType(APPLICATION_JSON)
                        .content(Reader.readJsonFile("/song/post.json")))
                .andReturn();
        System.out.println("Song creation result: " + songResult.getResponse().getContentAsString());

        List<Song> songs = songRepository.findAll();
        System.out.println("Songs created: " + songs);

        MvcResult albumResult = mockMvc.perform(post("/album")
                        .contentType(APPLICATION_JSON)
                        .content(Reader.readJsonFile("/album/post.json")))
                .andReturn();
        System.out.println("Album creation result: " + albumResult.getResponse().getContentAsString());

        List<Album> albums = albumRepository.findAll();
        System.out.println("Albums created: " + albums);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAuthorizedAccessToGetAlbumById() throws Exception {
        mockMvc.perform(get("/album/{id}", 1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUserAccessToGetAlbumById() throws Exception {
        mockMvc.perform(get("/album/{id}", 1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testAuthorizedAccessToUpdateAlbumInfo() throws Exception {
        String jsonContent = Reader.readJsonFile("/album/patch.json");
        mockMvc.perform(patch("/album/{id}", 2)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUserAccessToUpdateAlbumInfo() throws Exception {
        String jsonContent = Reader.readJsonFile("/album/patch.json");
        mockMvc.perform(patch("/album/{id}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAuthorizedAccessToDeleteAlbumById() throws Exception {
        mockMvc.perform(delete("/album/{id}", 1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUserAccessToDeleteAlbumById() throws Exception {
        mockMvc.perform(delete("/album/{id}", 2)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}