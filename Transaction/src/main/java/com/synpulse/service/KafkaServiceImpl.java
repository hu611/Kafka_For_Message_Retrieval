package com.synpulse.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synpulse.Constants;
import com.synpulse.pojo.Transaction;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    KafkaConsumer kafkaConsumer1;

    @Autowired
    @Qualifier("KafkaConsumer2")
    KafkaConsumer kafkaConsumer2;

    @Autowired
    @Qualifier("KafkaConsumer3")
    KafkaConsumer kafkaConsumer3;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    KafkaServiceImpl kafkaService;


    @Override
    public List<Transaction> findTransaction(String date, String userId, int pageNum) {
        List<Transaction> res = new ArrayList<>();
        String topic = getUserTopic(userId);
        int partition = getUserPartition(userId);

        int offset = getStartOffset(topic, partition, date);
        if(offset == -1) {
            //no transaction record
            return res;
        }

        String nextMonth = getNextMonth(date);
        int nextMonthOffset = getStartOffset(topic, partition, nextMonth);
        System.out.println(nextMonthOffset);
        int retrievedOffset = offset + pageNum * 10;
        if(retrievedOffset < nextMonthOffset) {
            //the retrieved offset is within the range
            res = getLatestMessageByOffset(topic, partition, retrievedOffset, kafkaConsumer3);
            return res.subList(0,Math.min(res.size(),nextMonthOffset - retrievedOffset));
        } else if(nextMonthOffset == -1) {
            res = getLatestMessageByOffset(topic, partition, retrievedOffset, kafkaConsumer3);
        }
        return res;
    }

    public String getNextMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.plusMonths(1).toString();
    }

    /**
     * get start offset for Kafka topic-partition
     * @param topic
     * @param partition
     * @param requestDate
     * @return
     */
    public int getStartOffset(String topic, int partition, String requestDate) {

        long monthInBetween = -1;
        int offset = 0;
        String redisKey = Constants.getPartitionOffsetRedisKey(topic, partition, requestDate);
        if(redisTemplate.hasKey(redisKey)) {
            System.out.println(redisKey + " cache hit");
            return Integer.parseInt(redisTemplate.opsForValue().get(redisKey).toString());
        }
        //System.out.println(kafkaConsumer.position(topicPartition));
        Transaction startTransaction = null;

        while (monthInBetween != 0 && monthInBetween != 1) {
            List<Transaction> transactionList = getLatestMessageByOffset(topic, partition, offset, kafkaConsumer1);
            if(transactionList == null || transactionList.size() == 0) {
                //no data for request date
                return -1;
            }
            startTransaction = transactionList.get(0);
            monthInBetween = compareTwoMonths(startTransaction.getDate(), requestDate);
            if(monthInBetween > 1) {
                offset += (monthInBetween-1) * 1000;
            }
            if(monthInBetween < 0) {
                //when there is no
                return -1;
            }

        }


        //add 100 to offset at a time to further reduce the search range
        LocalDate currentLocalDate = LocalDate.parse(startTransaction.getDate());
        LocalDate requestLocalDate = LocalDate.parse(requestDate);
        long upperOffset = offset;
        while (!currentLocalDate.getMonth().equals(requestLocalDate.getMonth())) {
            upperOffset += 100;
            List<Transaction> transactionList = getLatestMessageByOffset(topic, partition, (int)upperOffset, kafkaConsumer1);
            if(transactionList == null || transactionList.size() == 0) {
                break;
            }
            startTransaction = transactionList.get(0);
            currentLocalDate = LocalDate.parse(startTransaction.getDate());
        }


        long start_offset = Math.max(0,upperOffset - 100);
        // retrieve next upperOffset-upperoffset+100 records, and find the starting offset
        List<Transaction> transactionList = getLatestMessageByOffset(topic, partition, (int)start_offset, kafkaConsumer2);
        for(int i = 0; i < transactionList.size(); i++) {
            currentLocalDate = LocalDate.parse(transactionList.get(i).getDate());
            if(currentLocalDate.getMonth().equals(requestLocalDate.getMonth())) {
                redisTemplate.opsForValue().set(redisKey, String.valueOf(start_offset+i));
                return (int)start_offset + i;
            }
        }

        return -1;
    }

    public long compareTwoMonths(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate startRequestDate = LocalDate.parse(endDate);
        return ChronoUnit.MONTHS.between(startLocalDate, startRequestDate);
    }

    public String getUserTopic(String userId) {
        return "userList" + (Integer.parseInt(userId)/100 + 1);
    }

    public int getUserPartition(String userId) {
        return Integer.parseInt(userId)%100;
    }

    /**
     * return messages from the given topic and partition
     * @param topic
     * @param partition
     * @param offset
     * @return
     */
    public List<Transaction> getLatestMessageByOffset(String topic, int partition, int offset, KafkaConsumer kafkaConsumer) {
        ObjectMapper objectMapper = new ObjectMapper();
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        kafkaConsumer.seek(topicPartition, offset);

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
        List<Transaction> transactionList = new ArrayList<>();
        records.forEach(record -> {
            try {
                Transaction transaction = objectMapper.readValue(record.value(), Transaction.class);
                transactionList.add(transaction);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return transactionList;
    }


}
