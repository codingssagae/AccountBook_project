package csec.accountbook.domain.repository;

import csec.accountbook.domain.IncomeItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IncomeItemRepository {

    private final EntityManager em;

    public void save(IncomeItem incomeItem){
        em.persist(incomeItem);
    }
}
