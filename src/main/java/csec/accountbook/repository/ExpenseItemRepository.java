package csec.accountbook.repository;

import csec.accountbook.domain.ExpenseItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Long> {

}
