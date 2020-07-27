package club.moddedminecraft.tpsmod;

public class TpsStat {
    private int serverId;
    private long statTime;
    private double tps;

    public TpsStat() {

    }

    public TpsStat(int serverId, long statTime, double tps) {
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

    public long getStatTime() {
        return statTime;
    }

    public void setStatTime(long statTime) {
        this.statTime = statTime;
    }

    public double getTps() {
        return tps;
    }

    public void setTps(double tps) {
        this.tps = tps;
    }
}
