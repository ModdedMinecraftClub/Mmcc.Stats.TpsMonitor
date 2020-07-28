package club.moddedminecraft.tpsmod.bukkitplugin;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpPoster implements Runnable {

    private final Logger logger;
    private final Config config;
    private final double tps;

    public HttpPoster(Logger logger, Config config, double tps) {
        this.logger = logger;
        this.config = config;
        this.tps = tps;
    }

    @Override
    public void run() {
        String json = "{"
                + "\n    \"serverId\": " + config.getServerId() + ","
                + "\n    \"tps\": " + tps + "\n"
                + "}";

        URL url = null;
        try {
            url = new URL("http://localhost:5000/api/tps-stats");
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, "Exception while sending POST request", e);
            return;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception while sending POST request", e);
            return;
        }
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            logger.log(Level.SEVERE, "Exception while sending POST request", e);
        }
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Java-Client");
        connection.setRequestProperty("X-Auth-Token", config.getApiToken());
        connection.setDoOutput(true);
        try (
                OutputStream outputStream = connection.getOutputStream();
        ) {
            byte[] data = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(data, 0, data.length);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception while sending POST request", e);
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            logger.info("Successfully sent TPS stats." + response.toString());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception while sending POST request", e);
        }
    }
}

