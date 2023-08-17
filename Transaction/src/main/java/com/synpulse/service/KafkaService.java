package com.synpulse.service;


import com.synpulse.pojo.Transaction;

import java.util.List;

public interface KafkaService {
    public List<Transaction> findTransaction(String date, String userId, int pageNum);
}
