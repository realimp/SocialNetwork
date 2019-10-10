package ru.skillbox.socialnetwork.api.requests;
//created by Fatykhov Ildar

import com.fasterxml.jackson.annotation.JsonProperty;

public class DialogHistory {

    private long ts;
    private int pts;
    @JsonProperty("preview_length")
    private int previewLength;
    private int onLines;
    @JsonProperty("events_limit")
    private int eventsLimit;
    @JsonProperty("msgs_limit")
    private int mSgsLimit;
    @JsonProperty("max_msg_id")
    private int maxMsgId;

    public DialogHistory() {}

    public DialogHistory(long ts, int pts, int previewLength, int onLines, int eventsLimit, int mSgsLimit, int maxMsgId) {
        this.ts = ts;
        this.pts = pts;
        this.previewLength = previewLength;
        this.onLines = onLines;
        this.eventsLimit = eventsLimit;
        this.mSgsLimit = mSgsLimit;
        this.maxMsgId = maxMsgId;
    }

    public long getTs() {
        return ts;
    }

    public int getPts() {
        return pts;
    }

    public int getPreviewLength() {
        return previewLength;
    }

    public int getOnLines() {
        return onLines;
    }

    public int getEventsLimit() {
        return eventsLimit;
    }

    public int getMSgsLimit() {
        return mSgsLimit;
    }

    public int getMaxMsgId() {
        return maxMsgId;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public void setPreviewLength(int previewLength) {
        this.previewLength = previewLength;
    }

    public void setOnLines(int onLines) {
        this.onLines = onLines;
    }

    public void setEventsLimit(int eventsLimit) {
        this.eventsLimit = eventsLimit;
    }

    public void setMSgsLimit(int mSgsLimit) {
        this.mSgsLimit = mSgsLimit;
    }

    public void setMaxMsgId(int maxMsgId) {
        this.maxMsgId = maxMsgId;
    }
}
