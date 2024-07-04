package csec.accountbook.repository;

import csec.accountbook.domain.IncomeItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeItemRepository extends JpaRepository<IncomeItem,Long> {
    List<IncomeItem> findByMemberId(Long id);

    List<IncomeItem> findByMemberIdAndIncomePathContaining(Long memberId, String keyword);

}
