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
    
    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "invite_code ")
    private String inviteCode ;

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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
