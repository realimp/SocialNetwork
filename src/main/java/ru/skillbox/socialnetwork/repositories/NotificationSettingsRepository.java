package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
import ru.skillbox.socialnetwork.entities.NotificationSettings;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.List;

@Repository
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Integer> {

    @Query("SELECT l FROM NotificationSettings l JOIN l.person WHERE l.person=:person_id")
    List<NotificationSettings> findByPersonId(@Param("person_id") Person person);

    @Query("SELECT l FROM NotificationSettings l JOIN l.person WHERE l.person=:person_id AND notification_type_id=:code")
    NotificationSettings findByPersonIdAndSettingCode(@Param("person_id") Person person, Integer code);
}
