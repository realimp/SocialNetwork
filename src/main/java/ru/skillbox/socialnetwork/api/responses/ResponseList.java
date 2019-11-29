package ru.skillbox.socialnetwork.api.responses;

public class ResponseList<T> extends Response<T> {

    private long total;
    private long offset;
    private long perPage;

    public ResponseList() {
    }

    public ResponseList(T data) {
        super(data);
    }

    public ResponseList(T data, long total) {
        super(data);
        this.total = total;
    }

    public ResponseList(String error, T data) {
        super(error, data);
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
