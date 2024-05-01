package csec.accountbook.repository;

import csec.accountbook.domain.IncomeItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeItemRepository extends JpaRepository<IncomeItem,Long> {
}
