package csec.accountbook.repository;

import csec.accountbook.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Follow.PK> {

    List<Follow> findAllByFromUser(Long fromUser);
    List<Follow> findAllByToUser(Long toUser);
    Follow findByFromUserAndToUser(Long fromUser, Long toUser);

}
