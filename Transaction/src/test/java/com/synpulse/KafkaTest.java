package com.synpulse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synpulse.pojo.Transaction;
import com.synpulse.service.KafkaServiceImpl;
import com.synpulse.utils.JsonUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.DataInput;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class KafkaTest {
    @Autowired
    KafkaProducer<String,String> kafkaProducer;

    @Autowired
    KafkaConsumer<String, String> kafkaConsumer;

    @Autowired
    KafkaServiceImpl kafkaService;
    @Test
    void testAddKafkaTopic() throws Exception{
        //100 partitions per topic, one user, one partition
        for(int i = 1; i <= 31; i++) {
            String topicName = "userList" + i;
            String kafkaUrl = "localhost:9092";
            Properties properties = new Properties();
            properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);

            AdminClient adminClient = AdminClient.create(properties);
            try {
                // 创建topic前，可以先检查topic是否存在，如果已经存在，则不用再创建了
                Set<String> topics = adminClient.listTopics().names().get();
                if (topics.contains(topicName)) {
                    continue;
                }

                // 创建topic
                NewTopic newTopic = new NewTopic(topicName, 100, (short) 1);
                CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));
                result.all().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                adminClient.close();
            }
        }
        Thread.sleep(200000);

    }

    @Test
    void testProducer() {
        for(int i = 0; i <= 20; i++) {
            String value = "hello" + i;
            kafkaProducer.send(new ProducerRecord<>("userList1",1,Constants.generateTransactionId(),value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println(recordMetadata.topic()+" "+recordMetadata.partition());
                }
            });
        }

        kafkaProducer.close();
    }

    @Test
    void testConsumer(){
        String topic = "userList1";
        ObjectMapper objectMapper = new ObjectMapper();
        int partition = 10;
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        System.out.println(kafkaConsumer.position(topicPartition));
        kafkaConsumer.seek(topicPartition, 2000);

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
        System.out.println(records.count());
        records.forEach(record -> {
            try {
                Transaction transaction = objectMapper.readValue(record.value(), Transaction.class);
                System.out.println(transaction);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });




    }

    @Test
    void addSimulatedData() throws Exception{
        String[]currencyNameList = new String[]{"GBP","USD","CNY","CHF","CAD","KYD","KZT"};
        for(int c = 21; c <= 30; c++) {
            String topic = "userList" + c;
            Random random = new Random();
            //generate 4 months
            for (int i = 1; i <= 7; i++) {
                LocalDate localDate = LocalDate.of(2022, i, 1);
                int minValue = 1000;
                int maxValue = 2000;

                int randomValue = random.nextInt(maxValue - minValue + 1) + minValue;
                //for each month, 1000 records
                for (int b = 0; b < randomValue; b++) {
                    Transaction transaction = new Transaction();
                    transaction.setDate(localDate.toString());
                    transaction.setUID(generateRandomIdentifier());
                    transaction.setAccountIBAN("CH93-0000-0000-0000-0000-0");

                    String random_name = currencyNameList[random.nextInt(currencyNameList.length)];
                    transaction.setDescription("Online payment " + random_name);
                    transaction.setAmountWithCurrency(random_name + " " + random.nextInt(5000));
                    String transactionJson = JsonUtils._object_to_json(transaction).toString();
                    //100 partitions
                    for (int a = 0; a < 100; a++) {
                        int partition = a;
                        kafkaProducer.send(new ProducerRecord<>(topic, partition, Constants.generateTransactionId(), transactionJson), new Callback() {
                            @Override
                            public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                            }
                        });
                    }
                }
            }
        }
        Thread.sleep(20000);


    }

    @Test
    void addSimulatedData2() throws Exception{
        int c = 31;
        String[]currencyNameList = new String[]{"GBP","USD","CNY","CHF","CAD","KYD","KZT"};
        String topic = "userList" + c;
        Random random = new Random();
        //generate 4 months
        for (int i = 1; i <= 4; i++) {
            LocalDate localDate = LocalDate.of(2022, i, 1);
            int[]recordList = new int[]{2405,1023,1053,4000};
            //for each month, 1000 records
            for (int b = 0; b < recordList[i-1]; b++) {
                Transaction transaction = new Transaction();
                transaction.setDate(localDate.toString());
                transaction.setUID(generateRandomIdentifier());
                transaction.setAccountIBAN("CH93-0000-0000-0000-0000-0");

                String random_name = currencyNameList[random.nextInt(currencyNameList.length)];
                transaction.setDescription("Online payment " + random_name);
                transaction.setAmountWithCurrency(random_name + " " + random.nextInt(5000));
                String transactionJson = JsonUtils._object_to_json(transaction).toString();
                //100 partitions
                for (int a = 0; a < 2; a++) {
                    int partition = a;
                    kafkaProducer.send(new ProducerRecord<>(topic, partition, Constants.generateTransactionId(), transactionJson), new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            System.out.println(recordMetadata.topic() + " " + recordMetadata.partition());
                        }
                    });
                }
            }
        }
        Thread.sleep(20000);
    }

    public static String generateRandomIdentifier() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        String randomIdentifier = uuidStr.substring(0, 8) + "-" +
                uuidStr.substring(9, 13) + "-" +
                uuidStr.substring(14, 18) + "-" +
                uuidStr.substring(19, 23) + "-" +
                uuidStr.substring(24);
        return randomIdentifier.toUpperCase();
    }

    @Test
    void testRemoveKafkaTopic() {
        //100 partitions per topic, one user, one partition
        List<String> topicList = new ArrayList<>();
        for(int i = 0; i <= 50; i++) {
            String topicName = "userList" + i;
            topicList.add(topicName);
        }

        String kafkaUrl = "localhost:9092";
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        AdminClient adminClient = AdminClient.create(properties);
        adminClient.deleteTopics(topicList);
        adminClient.close();

    }

    @Test
    void testgetLatestMessageByOffset() {
        List<Transaction> transactionList = kafkaService.getLatestMessageByOffset("userList0",10,0, kafkaConsumer);
        System.out.println(transactionList.get(0));
    }
}
