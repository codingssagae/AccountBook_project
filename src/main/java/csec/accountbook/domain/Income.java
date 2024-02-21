package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Income {

    @Id @GeneratedValue
    @Column(name = "INCOME_ID")
    private Long id;

    @OneToOne(fetch = LAZY)
    private Member member;

    @OneToMany(mappedBy = "income")
    private List<IncomeItem> incomeItems = new ArrayList<>();

    private int totalIncomeAmount;

}
