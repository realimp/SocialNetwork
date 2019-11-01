package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
import ru.skillbox.socialnetwork.entities.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {

    @Query("SELECT n FROM Notification n LEFT JOIN n.author WHERE n.author=:person_id")
    List<Notification> findByPersonId(@Param("person_id") Integer personId);

    @Query("SELECT n FROM Notification n LEFT JOIN n.author WHERE n.author=:person_id and n.typeId=:type")
    List<Notification> findByTypeId(@Param("type") NotificationTypeCode code, @Param("person_id") Integer personId);

}
