package club.moddedminecraft.tpsmod;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

import static club.moddedminecraft.tpsmod.TpsMod.config;
import static club.moddedminecraft.tpsmod.TpsMod.logger;

public class HttpPoster implements Runnable {

    private final double tps;

    public HttpPoster(double tps) {
        this.tps = tps;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        TpsStat tpsStat = new TpsStat(config.getServerId(), LocalDateTime.now(), tps);
        String json = gson.toJson(tpsStat);

        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("https://localhost:5001/api/tps-stats");
            StringEntity requestEntity = new StringEntity(
                    json,
                    ContentType.APPLICATION_JSON
            );
            request.setHeader("User-Agent", "Java-Client");
            request.setHeader("X-Auth-Token", config.getApiToken());
            request.setEntity(requestEntity);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                logger.error("Error sending TPS data. HTTP Status code: " + statusCode);
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
