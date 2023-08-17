package com.synpulse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;


public class Constants {
    public final static String API_ACCESS_KEY = "3a53c0be7999a7358c2c7f074326a107";

    public final static String localhost = "host.docker.internal";

    public static String getCurrencyExchangeRedisKey(String currencyName) {
        return currencyName + "_RedisKey";
    }

    public static String getPartitionOffsetRedisKey(String topic, int partition, String requestDate) {
        LocalDate localDate = LocalDate.parse(requestDate);
        return topic + "-" + partition + "-" + localDate.getYear() + "_" + localDate.getMonth() + "_RedisKey";
    }

    public static String getHttpRequest(String url) {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return responseBody;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateTransactionId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").substring(0, 10);
    }


}
