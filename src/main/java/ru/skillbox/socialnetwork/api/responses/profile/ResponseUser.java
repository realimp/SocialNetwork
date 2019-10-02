package ru.skillbox.socialnetwork.api.responses.profile;

import java.util.Date;

public class ResponseUser {
    private Integer id;
    private String firstName;
    private String lastName;
    private Date regDate;
    private Date birthDate;
    private String eMail;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String messagesPermission; //Разрешение на получение сообщений: ALL(false) - от всех пользователей (кроме заблокированных), FRIENDS(true) - только от друзей
    private Date lastOnlineTime;
    private Boolean isBlocked; //Блокировка пользователя модератором / администратором
    private Boolean isFriend;

    public Integer getId() {
        return id;
    }

    public ResponseUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ResponseUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ResponseUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getRegDate() {
        return regDate;
    }

    public ResponseUser setRegDate(Date regDate) {
        this.regDate = regDate;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public ResponseUser setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String geteMail() {
        return eMail;
    }

    public ResponseUser seteMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ResponseUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public ResponseUser setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getAbout() {
        return about;
    }

    public ResponseUser setAbout(String about) {
        this.about = about;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ResponseUser setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ResponseUser setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getMessagesPermission() {
        return messagesPermission;
    }

    public ResponseUser setMessagesPermission(String messagesPermission) {
        this.messagesPermission = messagesPermission;
        return this;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public ResponseUser setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
        return this;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public ResponseUser setBlocked(Boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public ResponseUser setFriend(Boolean friend) {
        isFriend = friend;
        return this;
    }
}
