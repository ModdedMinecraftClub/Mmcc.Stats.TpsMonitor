package club.moddedminecraft.tpsmod;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigReader {
    public static final String PATH = "config/tpsmod.json";

    public Config readConfig() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(PATH));
        Config config = gson.fromJson(reader, Config.class);
        reader.close();

        return config;
    }
}
