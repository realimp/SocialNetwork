package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Response<T> {
    @JsonProperty private String error;
    @JsonProperty private long timestamp;
    @JsonProperty private T data;

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

    public void setError(String error) {
        this.error = error;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setData(T data) {
        this.data = data;
    }
}
