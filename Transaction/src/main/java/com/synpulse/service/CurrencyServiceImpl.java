package com.synpulse.service;

import com.synpulse.Constants;
import com.synpulse.pojo.Transaction;
import com.synpulse.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BigDecimal transferToHkd(String amount, String type) {
        return null;
    }

    /**
     * Retrieve latest currency change, and update to Redis
     */
    @Scheduled(cron = "0 0 */2 * * ?")
    @Override
    public void updateLatestCurrencyRate() {
        System.out.println("starting updating");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://api.exchangeratesapi.io/v1/latest?access_key=" + Constants.API_ACCESS_KEY; // 替换为你的目标 URL
                    String response = Constants.getHttpRequest(url);

                    //retrieve rates from response and convert to JsonNode
                    JsonNode ratesJsonNode = JsonUtils._string_to_json(response).get("rates");
                    updateCurrencyRateToRedis(ratesJsonNode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    public void updateCurrencyRateToRedis(JsonNode ratesJsonNode) {
        Map<String, String> hashMap = new HashMap<>();
        if (ratesJsonNode.isObject()) {
            ratesJsonNode.fieldNames().forEachRemaining(currencyName -> {
                JsonNode currencyValue = ratesJsonNode.get(currencyName);
                String redisKey = Constants.getCurrencyExchangeRedisKey(currencyName);
                hashMap.put(redisKey, currencyValue.toString());
            });
        }
        redisTemplate.opsForValue().multiSet(hashMap);

    }

    @Override
    public String convertCurrency(String from, String to, BigDecimal amount) {
        List<String> currencyNameList = new ArrayList<>();
        currencyNameList.add(Constants.getCurrencyExchangeRedisKey(from));
        currencyNameList.add(Constants.getCurrencyExchangeRedisKey(to));
        List<String> res = redisTemplate.opsForValue().multiGet(currencyNameList);
        new BigDecimal(res.get(0));
        amount = amount.divide(new BigDecimal(res.get(0)), MathContext.DECIMAL32);
        amount = amount.multiply(new BigDecimal(res.get(1)));
        return amount.toString();
    }

    @Override
    public void convertTransactionRecordsToHkd(List<Transaction> transactionList) {
        for(Transaction transaction: transactionList) {
            String[]amountInfo = transaction.getAmountWithCurrency().split(" ");
            String currencyName = amountInfo[0];
            String currentAmount = amountInfo[1];
            String HKDAmount = convertCurrency(currencyName, "HKD", new BigDecimal(currentAmount));
            transaction.setAmountWithCurrency("HKD "+ HKDAmount);
        }
    }


}
