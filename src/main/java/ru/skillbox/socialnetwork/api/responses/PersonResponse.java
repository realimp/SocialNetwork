package ru.skillbox.socialnetwork.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.skillbox.socialnetwork.entities.MessagePermission;

public class PersonResponse {

    private Integer id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("reg_date")
    private Long regDate;
    @JsonProperty("birth_date")
    private Long birthDate;
    @JsonProperty("email")
    private String eMail;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    @JsonProperty("messages_permission")
    private MessagePermission messagesPermission;
    @JsonProperty("last_online_time")
    private Long lastOnlineTime;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;

    @JsonProperty(value="is_blocked")
    public Boolean isBlocked() {
        return isBlocked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getRegDate() {
        return regDate;
    }

    public void setRegDate(Long regDate) {
        this.regDate = regDate;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public MessagePermission getMessagesPermission() {
        return messagesPermission;
    }

    public void setMessagesPermission(MessagePermission messagesPermission) {
        this.messagesPermission = messagesPermission;
    }

    public Long getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Long lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

}
