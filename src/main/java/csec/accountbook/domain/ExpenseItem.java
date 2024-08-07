package csec.accountbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
@Setter @Getter
public class ExpenseItem {

    @Id @GeneratedValue
    @Column(name = "EXPENSE_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String itemName;
    private int singleItemPrice;
    private int itemCount;
    private int totalItemPrice;

    @Enumerated(STRING)
    private ItemType itemType;
    private LocalDate purchaseDate;

    @OneToMany(mappedBy = "expenseItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage>images;

    private String imagePath; // 이미지 경로 필드 추가

    private String googleCalendarEventId;

}
