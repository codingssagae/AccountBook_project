package csec.accountbook.repository;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.Income;
import csec.accountbook.domain.IncomeItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Long> {
}
