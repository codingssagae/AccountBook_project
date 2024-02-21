package csec.accountbook.domain.repository;

import csec.accountbook.domain.Income;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IncomeRepository {

    private final EntityManager em;

    public void save(Income income){
        em.persist(income);
    }

}
