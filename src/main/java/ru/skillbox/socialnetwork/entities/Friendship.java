package ru.skillbox.socialnetwork.entities;

import javax.persistence.*;

@Entity
@Table(name="friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "src_person_id", nullable = false)
    private Person srcPerson;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dst_person_id", nullable = false)
    private Person dstPerson;

    public Friendship() {}

    public Friendship(Person user, Person friend, FriendshipStatus status) {
        code = status;
        dstPerson = user;
        srcPerson = friend;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FriendshipStatus getCode() {
        return code;
    }

    public void setCode(FriendshipStatus code) {
        this.code = code;
    }

    public Person getSrcPerson() {
        return srcPerson;
    }

    public void setSrcPerson(Person srcPerson) {
        this.srcPerson = srcPerson;
    }

    public Person getDstPerson() {
        return dstPerson;
    }

    public void setDstPerson(Person dstPerson) {
        this.dstPerson = dstPerson;
    }
}
