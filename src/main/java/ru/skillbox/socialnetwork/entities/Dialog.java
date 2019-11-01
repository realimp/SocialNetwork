//Автор:
//Имя: Дмитрий Хрипков
//Псевдоним: X64
//Почта: HDV_1990@mail.ru
package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dialog")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "unread_number")
    private Integer unreadNumber;

    @Column(name = "last_message")
    private Message lastMessage;

    public Dialog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnreadNumber() {
        return unreadNumber;
    }

    public void setUnreadNumber(Integer unreadNumber) {
        this.unreadNumber = unreadNumber;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
