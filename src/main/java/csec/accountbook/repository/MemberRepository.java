package csec.accountbook.repository;

import csec.accountbook.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Query("SELECT m.id FROM Member m where m.username =:username")
    Optional<Long> findIdByUsername(String username);

    Optional<Member> findByEmail(String email);


}
