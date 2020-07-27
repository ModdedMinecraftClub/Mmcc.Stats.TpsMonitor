package club.moddedminecraft.tpsmod;

import java.time.LocalDateTime;

public class TpsStat {
    private int serverId;
    private LocalDateTime statTime;
    private double tps;

    public TpsStat() {

    }

    public TpsStat(int serverId, LocalDateTime statTime, double tps) {
        this.serverId = serverId;
        this.statTime = statTime;
        this.tps = tps;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public LocalDateTime getStatTime() {
        return statTime;
    }

    public void setStatTime(LocalDateTime statTime) {
        this.statTime = statTime;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }
}
