package ru.skillbox.socialnetwork.api.responses;

public enum NotificationTypeCode {
    POST_COMMENT(1), COMMENT_COMMENT(2), FRIEND_REQUEST(3), MESSAGE(4), FRIEND_BIRTHDAY(5);

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
