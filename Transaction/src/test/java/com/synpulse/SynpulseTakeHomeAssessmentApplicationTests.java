package com.synpulse;

import com.fasterxml.jackson.databind.JsonNode;
import com.synpulse.pojo.Transaction;
import com.synpulse.service.CurrencyService;
import com.synpulse.service.KafkaServiceImpl;
import com.synpulse.utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@SpringBootTest
class SynpulseTakeHomeAssessmentApplicationTests {
    @Autowired
    CurrencyService currencyService;

    @Autowired
    KafkaServiceImpl kafkaService;

    @Autowired
    KafkaConsumer kafkaConsumer1;

    @Test
    void contextLoads() {
    }

    @Test
    void testCurrencyExchange() {
        try {
            currencyService.updateLatestCurrencyRate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testHttpRequest() throws Exception{
        currencyService.updateLatestCurrencyRate();
        Thread.sleep(20000);
    }

    @Test
    void testConvertCurrency() {
        String res = currencyService.convertCurrency("CNY","HKD",new BigDecimal(100));
        System.out.println(res);
    }

    @Test
    void testStartOffset() {
        System.out.println("start offset " + kafkaService.getStartOffset("userList3",80,"2022-02-01"));
    }

    @Test
    void testGetTransaction() {
        List<Transaction> transactionList = kafkaService.findTransaction("2022-02-01","282",1);
        for(Transaction transaction: transactionList) {
            System.out.println(transaction.toString());
        }
    }

    @Test
    void testTwoDates() {
        LocalDate currentLocalDate = LocalDate.of(2022,8,4);
        LocalDate requestLocalDate = LocalDate.of(2022,8,5);
        System.out.println(currentLocalDate.getMonth().equals(requestLocalDate.getMonth()));
    }

    @Test
    void testgetLatestMessageByOffset() {
        List<Transaction> transactionList = kafkaService.getLatestMessageByOffset("userList15",30,1005, kafkaConsumer1);
        System.out.println(transactionList.get(0));
    }



}
