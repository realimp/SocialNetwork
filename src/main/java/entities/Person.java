package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="person")
public class Person {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer id;

    @Column(name="FRIST_NAME", length = 30)
    private String firstName; // имя

    @Column(name="LAST_NAME", length = 30)
    private String lastName; // фамилия

    @Column(name="REG_DATE")
    private Date regDate; // дата и время регистрации

    @Column(name = "BIRTH_DATE")
    private Date birthDate; // дата рождения

    @Column(name = "E_MAIL", length = 50)
    private String eMail; //Привязанная электронная почта

    @Column(name = "PHONE", length = 50, nullable = false)
    private String phone; //Привязанный номер телефона

    @Column(name = "PASSWORD", length = 50)
    private String password; //Пароль

    @Column (name = "PHOTO", length = 50)
    private String photo; //Ссылка на изображение

    @Column (name = "ABOUT", length = 50)
    private String about; //Текст о себе

    @Column (name = "CITY", length = 50)
    private String city; //Город проживания

    @Column (name = "COUNTRY", length = 50)
    private String country; //Страна проживания

    @Column (name = "CONFIRMATION_CODE", length = 20)
    private String confirmationCode; //код восстановления пароля / подтверждения регистрации

    @Column (name = "IS_BLOCKED")
    private Boolean isApproved; //Подтверждена ли регистрация

    @Column (name = "MESSAGE_PERMISSION")
    private Boolean messagesPermission; //Разрешение на получение сообщений: ALL(false) - от всех пользователей (кроме заблокированных), FRIENDS(true) - только от друзей

    @Column (name = "LAST_ONLINE_TIME")
    private Date lastOnlineTime; //Время последнего пребывания онлайн

    @Column (name = "IS_BLOCKED")
    private Boolean isBlocked; //Блокировка пользователя модератором / администратором

    @Column (name = "IS_ONLINE")
    private Boolean isOnline; //Статус он-лайн

    @Column (name = "IS_DELETED")
    private Boolean isDeleted; //Удален ли аккаунт


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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getMessagesPermission() {
        return messagesPermission;
    }

    public void setMessagesPermission(Boolean messagesPermission) {
        this.messagesPermission = messagesPermission;
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}