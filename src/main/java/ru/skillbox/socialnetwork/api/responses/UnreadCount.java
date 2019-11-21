package ru.skillbox.socialnetwork.api.responses;

public class UnreadCount {
    private int count;

    public UnreadCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
