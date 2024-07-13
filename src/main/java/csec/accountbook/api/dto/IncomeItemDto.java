package csec.accountbook.api.dto;

import csec.accountbook.domain.IncomeItem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IncomeItemDto {

    private int incomeAmout;
    private String incomePath;
    private LocalDate incomeDate;

    public IncomeItemDto(IncomeItem incomeItem) {
        incomeAmout = incomeItem.getIncomeAmount();
        incomePath = incomeItem.getIncomePath();
        incomeDate = incomeItem.getIncomeDate();
    }
}
