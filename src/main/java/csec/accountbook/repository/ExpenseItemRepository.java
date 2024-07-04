package csec.accountbook.repository;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Long> {
    List<ExpenseItem> findByMemberId(Long memberId);

    List<ExpenseItem> findByItemType(ItemType itemType);

    List<ExpenseItem> findByMemberIdAndItemType(Long memberId, ItemType itemType);

    List<ExpenseItem> findByMemberIdAndItemNameContaining(Long memberId, String keyword);

    List<ExpenseItem> findByMemberIdAndItemTypeAndItemNameContaining(Long memberId, ItemType itemType, String keyword);
}
