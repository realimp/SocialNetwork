package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"error", "timestamp", "total", "offset", "perPage", "data"})
public class ResponseList<T> extends Response<T> {
    @JsonProperty
    private long total;
    @JsonProperty
    private long offset;
    @JsonProperty
    private long perPage;

    public ResponseList(T data) {
        super(data);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }
}
