package Entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String first_name; // имя
    private String last_name; // фамилия
    private Date reg_date; // дата и время регистрации
    private Date birth_date; // дата рождения
    private String e_mail; //Привязанная электронная почта
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
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