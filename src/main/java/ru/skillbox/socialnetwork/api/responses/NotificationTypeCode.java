package ru.skillbox.socialnetwork.api.responses;

public enum NotificationTypeCode {
    COMMENT_COMMENT(1), FRIEND_REQUEST(2), MESSAGE(3), POST(4), POST_COMMENT(5);

    private Integer code;

    NotificationTypeCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
