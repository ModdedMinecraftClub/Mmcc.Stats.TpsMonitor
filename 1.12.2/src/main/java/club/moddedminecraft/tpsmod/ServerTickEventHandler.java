package club.moddedminecraft.tpsmod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static club.moddedminecraft.tpsmod.TpsMod.logger;

public class ServerTickEventHandler {
    private int tickCount = 0;
    private long lastRecalculate = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            long currentTime = System.currentTimeMillis();
            if (lastRecalculate == 0) {
                lastRecalculate = currentTime;
            } else {
                long elapsed = System.currentTimeMillis() - lastRecalculate;
                if (elapsed >= 60 * 10 * 1000) {
                    double tps = ((double) tickCount) / (elapsed / 1000.0);
                    Thread postThread = new Thread(new HttpPoster(tps));
                    postThread.start();
                    lastRecalculate = currentTime;
                    tickCount = 0;
                }
            }
            tickCount++;
        }
    }
}
