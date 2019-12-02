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
            value = "select distinct fr2.src_person_id \n" +
                    "        from social_network.friendship fr1 \n" +
                    "        join social_network.friendship fr2 \n" +
                    "             on fr1.src_person_id = fr2.dst_person_id \n" +
                    "where fr1.dst_person_id = :person_id \n" +
                    "      and fr2.src_person_id <> :person_id \n" +
                    "      and not exists (select 1 from social_network.friendship fr \n " +
                    "          where fr.dst_person_id = :person_id and  fr.src_person_id = fr2.src_person_id) \n" +
                    "order by rand() limit 5")

    List<Integer> findRecommendations(@Param("person_id") Integer personId);

    @Query("SELECT f FROM Friendship f WHERE f.dstPerson=:person AND f.srcPerson=:friend")
    Friendship findByFriend(@Param("person") Person person, @Param("friend") Person friend);

    @Query("SELECT f FROM Friendship f WHERE f.dstPerson=:person")
    List<Friendship> findByFriends(@Param("person") Person person);
}
