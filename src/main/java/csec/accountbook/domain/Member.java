package csec.accountbook.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Column(unique = true)
    private String email;

    private String provider;

    private String providerId;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncomeItem> incomeItems;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseItem> expenseItems;

}
