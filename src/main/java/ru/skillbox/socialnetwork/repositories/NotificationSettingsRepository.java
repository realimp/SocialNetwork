package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.NotificationSettings;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.List;

@Repository
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Integer> {

    @Query("SELECT l FROM NotificationSettings l JOIN l.person WHERE l.person=:person_id")
    List<NotificationSettings> findByPersonId(@Param("person_id") Person person);

    @Query("SELECT l FROM NotificationSettings l JOIN l.person WHERE l.person=:person_id AND notification_type_id=:code")
    NotificationSettings findByPersonIdAndSettingCode(@Param("person_id") Person person, Integer code);

    @Query(nativeQuery = true, value = "SELECT * FROM notification_settings l WHERE l.notification_type_id=:type AND l.enable=1")
    List<NotificationSettings> findByTypeIdAndEnabled(@Param("type") int type);

    @Query(nativeQuery = true, value = "SELECT * FROM notification_settings l WHERE l.notification_type_id=:type AND l.enable=1")
    Page<NotificationSettings> findByTypeIdAndEnabled(@Param("type") int type, Pageable pageable);
}
