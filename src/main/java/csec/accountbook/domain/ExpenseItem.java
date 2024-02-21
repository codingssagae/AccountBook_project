package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.EnumType.*;

@Entity
@Setter @Getter
public class ExpenseItem {

    @Id @GeneratedValue
    @Column(name = "EXPENSE_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPENSE_ID")
    private Expense expense;

    private String itemName;
    private int itemPrice;
    private int itemCount;

    @Enumerated(STRING)
    private ItemType itemType;

    public void setExpense(Expense expense){
        this.expense = expense;
        expense.getExpenseItems().add(this);
    }

}
