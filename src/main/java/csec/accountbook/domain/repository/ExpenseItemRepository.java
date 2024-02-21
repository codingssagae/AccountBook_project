package csec.accountbook.domain.repository;

import csec.accountbook.domain.ExpenseItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpenseItemRepository {

    private final EntityManager em;

    public void save(ExpenseItem expenseItem){
        em.persist(expenseItem);
    }

}
