package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationBase {

    private Integer id;
    @JsonProperty("event_type")
    private NotificationTypeCode eventType;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("entity_id")
    private Integer entityId;
    @JsonProperty("entity_author")
    private BasicPerson entityAuthor;
    private String info;

    public NotificationBase() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NotificationTypeCode getEventType() {
        return eventType;
    }

    public void setEventType(NotificationTypeCode eventType) {
        this.eventType = eventType;
    }

    public Long getSentTime() {
        return sentTime;
    }

    public void setSentTime(Long sentTime) {
        this.sentTime = sentTime;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public BasicPerson getEntityAuthor() {
        return entityAuthor;
    }

    public void setEntityAuthor(BasicPerson entityAuthor) {
        this.entityAuthor = entityAuthor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
