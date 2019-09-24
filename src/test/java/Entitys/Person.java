package Entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String first_name; // имя


    @Getter
    @Setter
    private String last_name; // фамилия


    @Getter
    @Setter
    private Date reg_date; // дата и время регистрации

    @Getter
    @Setter
    private Date birth_date; // дата рождения


    @Getter
    @Setter
    private String e_mail; //Привязанная электронная почта


    @Getter
    @Setter
    private String phone; //Привязанный номер телефона


    @Getter
    @Setter
    private String password; //Пароль


    @Getter
    @Setter
    private String photo; //Ссылка на изображение


    @Getter
    @Setter
    private String about; //Текст о себе


    @Getter
    @Setter
    private String city; //Город проживания

    @Getter
    @Setter
    private String country; //Страна проживания


    @Getter
    @Setter
    private String confirmation_code; //код восстановления пароля / подтверждения регистрации


    @Getter
    @Setter
    private Boolean is_approved; //Подтверждена ли регистрация


    @Getter
    @Setter
    private Boolean messages_permission; //Разрешение на получение сообщений: ALL(false) - от всех пользователей (кроме заблокированных), FRIENDS(true) - только от друзей


    @Getter
    @Setter
    private Date last_online_time; //Время последнего пребывания онлайн


    @Getter
    @Setter
    private Boolean is_blocked; //Блокировка пользователя модератором / администратором

}