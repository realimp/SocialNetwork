package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName; // имя
    private String lastName; // фамилия
    private Date regDate; // дата и время регистрации
    private Date birthDate; // дата рождения
    private String eMail; //Привязанная электронная почта
    private String phone; //Привязанный номер телефона
    private String password; //Пароль
    private String photo; //Ссылка на изображение
    private String about; //Текст о себе
    private String city; //Город проживания
    private String country; //Страна проживания
    private String confirmationCode; //код восстановления пароля / подтверждения регистрации
    private Boolean isApproved; //Подтверждена ли регистрация
    private Boolean messagesPermission; //Разрешение на получение сообщений: ALL(false) - от всех пользователей (кроме заблокированных), FRIENDS(true) - только от друзей
    private Date lastOnlineTime; //Время последнего пребывания онлайн
    private Boolean isBlocked; //Блокировка пользователя модератором / администратором

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
}