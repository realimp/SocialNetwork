package ru.skillbox.socialnetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.entities.Friendship;
import ru.skillbox.socialnetwork.entities.FriendshipStatus;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("SELECT f FROM Friendship f WHERE f.dstPerson=:person AND f.code=:status")
    List<Friendship> findByFriends(@Param("person") Person person, @Param("status") FriendshipStatus status);

    @Query(nativeQuery = true,
            value = "with personIds as (SELECT distinct src_person_id FROM social_network.friendship where dst_person_id = :person_id)" +
                    "SELECT distinct src_person_id FROM social_network.friendship where \n" +
            "dst_person_id in (SELECT distinct src_person_id FROM personIds) \n" +
            "and src_person_id <> :person_id \n" +
            "and src_person_id not in (SELECT distinct src_person_id FROM personIds)  \n" +
            "ORDER BY RAND() limit 5")
    List<Integer> findRecommendations(@Param("person_id") Integer personId);

    @Query("SELECT f FROM Friendship f WHERE f.dstPerson=:person AND f.srcPerson=:friend AND f.code=:status")
    Friendship findByFriend(@Param("person") Person person, @Param("friend") Person friend, @Param("status") FriendshipStatus status);

}
