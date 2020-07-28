package club.moddedminecraft.tpsmod.bukkitplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class TpsPlugin extends JavaPlugin {

    private Logger logger;
    private Config config;

    @Override
    public void onEnable() {
        // copy default config if one doesn't exist already;
        this.saveDefaultConfig();

        // set up logger and config;
        logger = Bukkit.getLogger();
        this.setupConfig();

        // register our runnable
        new TickRunnable(logger, config).runTaskTimer(this, 0L, 1L);
    }

    @Override
    public void onDisable() {
        //Fired when the server stops and disables all plugins;
    }

    private void setupConfig() {
        String apiToken = this.getConfig().getString("apiToken");
        int serverId = this.getConfig().getInt("serverId");
        config = new Config(apiToken, serverId);
    }
}
