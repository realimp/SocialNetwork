package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
import ru.skillbox.socialnetwork.entities.Notification;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {

    @Query("SELECT n FROM Notification n WHERE n.recipient=:recipient_id")
    List<Notification> findByPersonId(@Param("recipient_id")Person person);

    @Query("SELECT n FROM Notification n LEFT JOIN n.author WHERE n.recipient=:recipient_id and n.typeId=:type")
    List<Notification> findByTypeId(@Param("type") NotificationTypeCode code, @Param("recipient_id") Integer personId);

}
