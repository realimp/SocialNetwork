package ru.skillbox.socialnetwork.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EditPerson {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("birth_date")
    private Date birthDate;
    private String phone;
    @JsonProperty("photo_id")
    private String photoId;
    private String about;
    private String city;
    private String country;
    @JsonProperty("messages_permission")
    private String messagesPermission;

    public EditPerson() {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getMessagesPermission() {
        return messagesPermission;
    }

    public void setMessagesPermission(String messagesPermission) {
        this.messagesPermission = messagesPermission;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String cityId) {
        this.city = cityId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String countryId) {
        this.country = countryId;
    }
}
