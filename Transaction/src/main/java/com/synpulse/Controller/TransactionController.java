package com.synpulse.Controller;

import com.synpulse.pojo.Transaction;
import com.synpulse.service.CurrencyService;
import com.synpulse.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    KafkaService kafkaService;

    @Autowired
    CurrencyService currencyService;

    @RequestMapping("/transaction")
    @ResponseBody
    public List<Transaction> getTransactionList(@RequestParam("year") String year, @RequestParam("month") String month, @RequestParam("pageNum") int pageNum) {
        if(pageNum <= 0) {
            return new ArrayList<>();
        }
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),1);
        List<Transaction> rawTransaction = kafkaService.findTransaction(localDate.toString(), get_token_userId(),pageNum);
        currencyService.convertTransactionRecordsToHkd(rawTransaction);
        return rawTransaction;
    }

    public static String get_token_userId() {
        Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principalObj.toString();
    }
}
