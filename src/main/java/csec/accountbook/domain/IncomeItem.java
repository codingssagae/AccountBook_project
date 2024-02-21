package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class IncomeItem {

    @Id @GeneratedValue
    @Column(name = "INCOME_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INCOME_ID")
    private Income income;

    private int incomeAmount;

    @Column(length = 255)
    private String incomePath;

    public void setIncome(Income income){
        this.income = income;
        income.getIncomeItems().add(this);
    }

}
