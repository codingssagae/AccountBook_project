package csec.accountbook.domain.repository;

import csec.accountbook.domain.Expense;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpenseRepository {

    private final EntityManager em;

    public void save(Expense expense){
        em.persist(expense);
    }

}
