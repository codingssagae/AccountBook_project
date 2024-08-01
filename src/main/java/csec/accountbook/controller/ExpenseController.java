package csec.accountbook.controller;

import csec.accountbook.domain.ExpenseItem;
import csec.accountbook.domain.ItemType;
import csec.accountbook.repository.ExpenseItemRepository;
import csec.accountbook.service.ExpenseItemService;
import csec.accountbook.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseItemService expenseItemService;
    private final GoogleCalendarService googleCalendarService;
    private final ExpenseItemRepository expenseItemRepository;

    @GetMapping("/expenseItemForm")
    public String showExpenseForm(Model model) {
        model.addAttribute("expenseItem", new ExpenseItem());
        model.addAttribute("itemTypes", ItemType.values());
        return "expenseItemForm";
    }

    @PostMapping("/expenses/save")
    public String saveExpense(@ModelAttribute ExpenseItem expenseItem, BindingResult result,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @RegisteredOAuth2AuthorizedClient("google")OAuth2AuthorizedClient authorizedClient) {
        if (result.hasErrors()) {
            return "expenseItemForm";
        }

        try {

            String eventId = googleCalendarService.addEvent(
                    authorizedClient,
                    expenseItem.getItemName(),
                    null,
                    "이것을 구매함 : " + expenseItem.getItemName(),
                    LocalDateTime.of(expenseItem.getPurchaseDate(), LocalDateTime.now().toLocalTime()),
                    LocalDateTime.of(expenseItem.getPurchaseDate(), LocalDateTime.now().toLocalTime().plusHours(1))

            );

            ExpenseItem item = expenseItemService.save(expenseItem.getItemName(), expenseItem.getSingleItemPrice(),
                    expenseItem.getItemCount(), expenseItem.getItemType(), userDetails.getUsername(),
                    expenseItem.getPurchaseDate(), imageFile, eventId);

            System.out.println("eventId = " + eventId);

        } catch (IOException|GeneralSecurityException e) {
            e.printStackTrace();
            return "expenseItemForm";
        }

        return "redirect:/accountBookMenu";
    }

    @PostMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id,
                                @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) throws GeneralSecurityException, IOException {
        Optional<ExpenseItem> expenseItem = expenseItemRepository.findById(id);
        googleCalendarService.deleteEvent(authorizedClient, expenseItem.get().getGoogleCalendarEventId());
        expenseItemService.delete(id);
        return "redirect:/expenseItemList";
    }
}
