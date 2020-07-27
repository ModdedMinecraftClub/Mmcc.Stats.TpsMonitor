package club.moddedminecraft.tpsmod;

public class Config {
    private String apiToken;
    private int serverId;

    public Config() {

    }

    public Config(String apiToken, int serverId) {
        this.apiToken = apiToken;
        this.serverId = serverId;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
