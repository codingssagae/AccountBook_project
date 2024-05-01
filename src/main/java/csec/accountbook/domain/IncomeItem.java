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

    private int incomeAmount;

    @Column(length = 255)
    private String incomePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
