package csec.accountbook.repository;

import csec.accountbook.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Follow.PK> {

    @Query("SELECT f FROM Follow f WHERE f.fromUser = :fromUser")
    List<Follow> findAllByFromUser(@Param("fromUser") Long fromUser);

    @Query("SELECT f FROM Follow f WHERE f.toUser = :toUser")
    List<Follow> findAllByToUser(@Param("toUser") Long toUser);

    Follow findByFromUserAndToUser(Long fromUser, Long toUser);



}
