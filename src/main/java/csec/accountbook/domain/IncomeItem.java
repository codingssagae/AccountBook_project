package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private LocalDate incomeDate;

    @OneToMany(mappedBy = "incomeItem", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostImage> images;

}
