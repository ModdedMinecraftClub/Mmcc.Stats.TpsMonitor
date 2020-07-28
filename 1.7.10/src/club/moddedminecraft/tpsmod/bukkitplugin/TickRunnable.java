package club.moddedminecraft.tpsmod.bukkitplugin;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class TickRunnable extends BukkitRunnable {

    private final Logger logger;
    private final Config config;

    private int tickCount = 0;
    private long lastRecalculate = 0;

    public TickRunnable(Logger logger, Config config) {
        this.logger = logger;
        this.config = config;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (lastRecalculate == 0) {
            lastRecalculate = currentTime;
        } else {
            long elapsed = System.currentTimeMillis() - lastRecalculate;
            if (elapsed >= 60 * 10 * 1000) {
                double tps = ((double) tickCount) / (elapsed / 1000.0);
                Thread postThread = new Thread(new HttpPoster(logger, config, tps));
                postThread.start();
                lastRecalculate = currentTime;
                tickCount = 0;
            }
        }
        tickCount++;
    }
}
