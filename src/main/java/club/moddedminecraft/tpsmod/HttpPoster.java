package club.moddedminecraft.tpsmod;

import com.google.gson.Gson;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
        TpsStat tpsStat = new TpsStat(config.getServerId(), System.currentTimeMillis(), tps);
        String json = gson.toJson(tpsStat);

        logger.info(json);
        System.out.println(json);

        URL url = null;
        try {
            url = new URL("http://localhost:5000/api/tps-stats");
        } catch (MalformedURLException e) {
            logger.error(e);
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            logger.error(e);
        }
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            logger.error(e);
        }
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Java-Client");
        connection.setRequestProperty("X-Auth-Token", config.getApiToken());
        connection.setRequestProperty("X-Auth-Token", config.getApiToken());
        connection.setDoOutput(true);
        try(
                OutputStream outputStream = connection.getOutputStream();
        ){
            byte[] data = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(data, 0, data.length);
        } catch (IOException e) {
            logger.error(e);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString()); //do whatever you want with the response here
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
