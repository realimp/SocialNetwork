package ru.skillbox.socialnetwork.api.requests;

import java.util.Date;

public class EditPerson {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phone;
    private String photoId;
    private String about;
    private Integer cityId;
    private Integer countryId;
    private String messagesPermission;


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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getMessagesPermission() {
        return messagesPermission;
    }

    public void setMessagesPermission(String messagesPermission) {
        this.messagesPermission = messagesPermission;
    }
}
