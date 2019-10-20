package ru.skillbox.socialnetwork.api.responses;

public class LongpolServerResponseBody {

    private String key;
    private String server;
    private Long ts;

    public LongpolServerResponseBody() {}

    public LongpolServerResponseBody(String key, String server, Long ts) {
        this.key = key;
        this.server = server;
        this.ts = ts;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

}
