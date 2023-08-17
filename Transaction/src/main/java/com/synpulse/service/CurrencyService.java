package com.synpulse.service;

import com.synpulse.pojo.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyService {
    public BigDecimal transferToHkd(String amount, String type);

    public void updateLatestCurrencyRate();

    public String convertCurrency(String from, String to, BigDecimal amount);

    public void convertTransactionRecordsToHkd(List<Transaction> transactionList);
}
