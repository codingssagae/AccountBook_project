package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Setter @Getter
public class Expense {

    @Id @GeneratedValue
    @Column(name = "EXPENSE_ID")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "expense",cascade = CascadeType.ALL)
    private List<ExpenseItem> expenseItems = new ArrayList<>();

    private int totalExpenseAmount;

    private int foodExpenseAmount;
    private int clothesExpenseAmount;




}
