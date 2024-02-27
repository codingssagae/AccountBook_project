package csec.accountbook.repository;

import csec.accountbook.domain.Expense;
import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
