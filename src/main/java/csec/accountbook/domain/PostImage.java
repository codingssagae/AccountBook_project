package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PostImage {

    @Id @GeneratedValue
    private Long id;

    private String imagePath;

    private String title;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ITEM_ID")
    private ExpenseItem expenseItem;

    @ManyToOne
    @JoinColumn(name = "INCOME_ITEM_ID")
    private IncomeItem incomeItem;


}
