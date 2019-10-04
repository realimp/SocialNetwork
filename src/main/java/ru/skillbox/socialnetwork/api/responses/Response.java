package ru.skillbox.socialnetwork.api.responses;

import java.util.Date;

public class Response<T extends Object> {
    private String error;
    private long timestamp;
    private T data;

    public Response(T data) {
        this.data = data;
        this.timestamp = new Date().getTime();
    }

    public Response(String error, long timestamp, T data) {
        this.error = error;
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }
}
