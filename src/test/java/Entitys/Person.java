package Entitys;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
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
}