package dbp.proyecto.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class Reader {
    @Autowired
    ObjectMapper mapper;

    public static String readJsonFile(String fileName) throws IOException {
        Path path = new ClassPathResource(fileName).getFile().toPath();
        return Files.readString(path);
    }

    public static String updateAlbumTitle(String jsonContent, String key, String newValue) {
        return jsonContent.replaceFirst("(?<=\"" + key + "\":\")[^\"]*", newValue);
    }
}