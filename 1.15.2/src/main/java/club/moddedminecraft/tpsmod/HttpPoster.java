package club.moddedminecraft.tpsmod;

import com.google.gson.Gson;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static club.moddedminecraft.tpsmod.TpsMod.CONFIG;
import static club.moddedminecraft.tpsmod.TpsMod.LOGGER;

public class HttpPoster implements Runnable {

    private final double tps;

    public HttpPoster(double tps) {
        this.tps = tps;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        TpsStat tpsStat = new TpsStat(CONFIG.getServerId(), System.currentTimeMillis(), tps);
        String json = gson.toJson(tpsStat);
        URL url = null;
        try {
            url = new URL("http://localhost:5000/api/tps-stats");
        } catch (MalformedURLException e) {
            LOGGER.error(e);
            return;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LOGGER.error(e);
            return;
        }
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            LOGGER.error(e);
        }
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Java-Client");
        connection.setRequestProperty("X-Auth-Token", CONFIG.getApiToken());
        connection.setDoOutput(true);
        try (
                OutputStream outputStream = connection.getOutputStream();
        ) {
            byte[] data = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(data, 0, data.length);
        } catch (IOException e) {
            LOGGER.error(e);
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            LOGGER.info("Successfully sent TPS stats." + response.toString());
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
