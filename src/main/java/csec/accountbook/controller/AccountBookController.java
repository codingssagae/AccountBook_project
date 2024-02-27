package csec.accountbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AccountBookController {

    @GetMapping("/accountBook")
    @ResponseBody
    public String showAccountBook(Model model){
        return "가계부 목록";
    }

}
