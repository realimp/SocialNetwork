package ru.skillbox.socialnetwork.api.requests;
//created by Fatykhov Ildar

public class DialogHistory {

    private long ts;
    private int pts;
    private int preview_length;
    private int onlines;
    private int events_limit;
    private int msgs_limit;
    private int max_msg_id;

    public DialogHistory() {}

    public DialogHistory(long ts, int pts, int preview_length, int onlines, int events_limit, int msgs_limit, int max_msg_id) {
        this.ts = ts;
        this.pts = pts;
        this.preview_length = preview_length;
        this.onlines = onlines;
        this.events_limit = events_limit;
        this.msgs_limit = msgs_limit;
        this.max_msg_id = max_msg_id;
    }

    public long getTs() {
        return ts;
    }

    public int getPts() {
        return pts;
    }

    public int getPreview_length() {
        return preview_length;
    }

    public int getOnlines() {
        return onlines;
    }

    public int getEvents_limit() {
        return events_limit;
    }

    public int getMsgs_limit() {
        return msgs_limit;
    }

    public int getMax_msg_id() {
        return max_msg_id;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public void setPreview_length(int preview_length) {
        this.preview_length = preview_length;
    }

    public void setOnlines(int onlines) {
        this.onlines = onlines;
    }

    public void setEvents_limit(int events_limit) {
        this.events_limit = events_limit;
    }

    public void setMsgs_limit(int msgs_limit) {
        this.msgs_limit = msgs_limit;
    }

    public void setMax_msg_id(int max_msg_id) {
        this.max_msg_id = max_msg_id;
    }
}
