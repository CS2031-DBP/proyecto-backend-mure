package dbp.proyecto.notification.domain;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";
    private final OkHttpClient client = new OkHttpClient();

    public void sendNotification(String expoToken, String title, String message) {
        JsonObject json = new JsonObject();
        json.addProperty("to", expoToken);
        json.addProperty("sound", "default");
        json.addProperty("title", title);
        json.addProperty("body", message);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(EXPO_PUSH_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
