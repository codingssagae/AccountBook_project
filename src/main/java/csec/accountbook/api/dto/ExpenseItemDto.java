package csec.accountbook.api.dto;

import csec.accountbook.domain.ExpenseItem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseItemDto {

    private String itemName;
    private int itemCount;
    private int singleItemPrice;
    private int totalItemPrice;
    private LocalDate purchaseDate;

    public ExpenseItemDto(ExpenseItem expenseItem) {
        itemName = expenseItem.getItemName();
        itemCount = expenseItem.getItemCount();
        singleItemPrice = expenseItem.getSingleItemPrice();
        totalItemPrice = expenseItem.getTotalItemPrice();
        purchaseDate = expenseItem.getPurchaseDate();
    }
}
