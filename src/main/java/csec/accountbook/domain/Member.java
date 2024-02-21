package csec.accountbook.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToOne(mappedBy = "member")
    private Expense expense;

    @OneToOne(mappedBy = "member")
    private Income income;

    private String name;
    private int age;

}
