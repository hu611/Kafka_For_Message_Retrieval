package com.synpulse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synpulse.pojo.Transaction;
import com.synpulse.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DeployUnitTest {
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testAuthRegistration() throws Exception{
        String url = getAuthUrl("1530", "123");
        ResponseEntity<String> response = restTemplate.postForEntity(url,null, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testNormalTransaction() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 22096.155270735\",\"date\":\"2022-02-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"9361ADDA-E913-47B4-AF1E-C05EFB1A99FB\"},{\"amountWithCurrency\":\"HKD 33205.279670661\",\"date\":\"2022-02-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"CD4A4A03-01CC-49DC-BCF9-E7911BE8C83B\"},{\"amountWithCurrency\":\"HKD 436.07144697543\",\"date\":\"2022-02-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"49A65BE5-E0EC-4B64-B692-DDE8BA6AD47C\"},{\"amountWithCurrency\":\"HKD 14652.431582754\",\"date\":\"2022-02-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"8482B239-905D-4EE3-ADC7-80980692C5ED\"},{\"amountWithCurrency\":\"HKD 18019.936334694\",\"date\":\"2022-02-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"422B2A61-35FB-44BB-A594-F039A011DC00\"},{\"amountWithCurrency\":\"HKD 3422.3664425895\",\"date\":\"2022-02-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"756F3924-E992-4D02-9B6D-FFA71B1DA1AA\"},{\"amountWithCurrency\":\"HKD 9890.996638866\",\"date\":\"2022-02-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A5B5B5FF-DEC2-4B70-A655-55FB87247771\"},{\"amountWithCurrency\":\"HKD 15639.499615761\",\"date\":\"2022-02-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"AA07BB7D-00B0-4CF7-9BA2-A7BBF057A4EF\"},{\"amountWithCurrency\":\"HKD 3201.7958171808\",\"date\":\"2022-02-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"F306ED35-78DA-436A-84DF-6DCC4D901B98\"},{\"amountWithCurrency\":\"HKD 25042.309306833\",\"date\":\"2022-02-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"1AEEB206-7547-444E-AC87-5063C3F42B76\"}]";
        testConnectionTemplate("1530",2022,2,1, expectedJson);
    }

    @Test
    public void testNormalTransaction2() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 6942.1781808225\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"F327F4DE-1A2D-4DB7-B543-CF853CE5DFC7\"},{\"amountWithCurrency\":\"HKD 9637.101100620\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"81596DA1-57EB-4349-83D0-019428A850CB\"},{\"amountWithCurrency\":\"HKD 41340.611297475\",\"date\":\"2022-05-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"B162E80D-2794-4C7E-864F-627E5F3B1C33\"},{\"amountWithCurrency\":\"HKD 48127.134268080\",\"date\":\"2022-05-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"1A4B915E-EFD9-4111-8090-03AC5DDE1B4E\"},{\"amountWithCurrency\":\"HKD 2246.0576413905\",\"date\":\"2022-05-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"88ADEC47-E98A-4670-9C79-495B580436C3\"},{\"amountWithCurrency\":\"HKD 3525.9107118165\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"2EC20E95-40B9-4030-A3FB-F07512381CBD\"},{\"amountWithCurrency\":\"HKD 3028.5075587445\",\"date\":\"2022-05-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"8C849D5F-DD3B-4C6D-866A-187160A0CFAE\"},{\"amountWithCurrency\":\"HKD 46.084328020710\",\"date\":\"2022-05-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"C7C8DF37-2FBD-4628-A1AC-C04443C23B4E\"},{\"amountWithCurrency\":\"HKD 26858.995155720\",\"date\":\"2022-05-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"8E98EBD3-5A39-4561-AC4D-7395A7A3101C\"},{\"amountWithCurrency\":\"HKD 1442.6805994020\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"853BB22C-AB63-4704-9016-F2C782D847A9\"}]";
        testConnectionTemplate("1530",2022,5,4, expectedJson);
    }

    @Test
    public void testNormalTransaction3() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 39536.533783761\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"D268B484-7FB7-414A-ABAB-54ADBCE19111\"},{\"amountWithCurrency\":\"HKD 31565.166234777\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"8D59F0F0-2CD3-423A-B2D1-264BDC058A22\"},{\"amountWithCurrency\":\"HKD 11503.159184391\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"72CAF8BF-AE0E-4DE2-80B2-125170B9F660\"},{\"amountWithCurrency\":\"HKD 70.820721194118\",\"date\":\"2022-03-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A4A386EF-541D-4CA7-A4F3-C07821A07AB9\"},{\"amountWithCurrency\":\"HKD 11817.998251899\",\"date\":\"2022-03-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A11F5478-876A-4CD2-855A-8D8947BC7FEC\"},{\"amountWithCurrency\":\"HKD 23583.344845683\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"D1B62B00-C721-4AE3-9D3B-27A6523B8F1B\"},{\"amountWithCurrency\":\"HKD 9075.675522165\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"3548B35E-C63B-4734-B84E-8E8FB7D82CAA\"},{\"amountWithCurrency\":\"HKD 10769.792553975\",\"date\":\"2022-03-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"9646019E-0F34-49B1-8D95-C3455F07C4AD\"},{\"amountWithCurrency\":\"HKD 28714.047076386\",\"date\":\"2022-03-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"9D6EA360-82A5-43F6-9DF3-3380E0F2A68E\"},{\"amountWithCurrency\":\"HKD 34352.865429834\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"F65F2828-DBD3-47EA-A2C3-078DB8598A4D\"}]";
        testConnectionTemplate("42",2022,3,4, expectedJson);
    }

    @Test
    public void testNormalTransaction4() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 22888.690947320\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"2C5AE64F-7AE1-47CA-A3CA-7D592F6B3F03\"},{\"amountWithCurrency\":\"HKD 33.732788240732\",\"date\":\"2022-03-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"00783184-FB40-4A2E-AF72-47C3C106A8F6\"},{\"amountWithCurrency\":\"HKD 0\",\"date\":\"2022-03-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"192B2B18-E992-468A-B8CE-1AA79578C2B1\"},{\"amountWithCurrency\":\"HKD 2485.9567577220\",\"date\":\"2022-03-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"132D816E-2EB9-4BBB-9C2B-F14C233B3F1B\"},{\"amountWithCurrency\":\"HKD 4455.5812412878\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"4AE61DDC-F48F-4264-A703-61393FB39BB1\"},{\"amountWithCurrency\":\"HKD 50.861793065120\",\"date\":\"2022-03-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"BE279054-E5D2-4EE6-9D35-E801B729D765\"},{\"amountWithCurrency\":\"HKD 7652.1331688318\",\"date\":\"2022-03-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"F31ED8DC-B5A3-482A-ABAA-4B4AA35435A2\"},{\"amountWithCurrency\":\"HKD 15883.276483958\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"AABDB594-AEE8-418B-A6C7-DD9636C26649\"},{\"amountWithCurrency\":\"HKD 7292.5767562668\",\"date\":\"2022-03-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"EE17F1A2-CEF3-47C6-BFA7-F1FACB7E933C\"},{\"amountWithCurrency\":\"HKD 17046.982013942\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"9D6D5D88-91EF-4EF7-9EAA-1763DD7FFD45\"}]";
        testConnectionTemplate("425",2022,3,36, expectedJson);
    }

    @Test
    public void testFirstPageTransaction() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 4746.1496182092\",\"date\":\"2022-01-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"99F95AE7-F6B9-46D0-BBEF-856A9B9DEB02\"},{\"amountWithCurrency\":\"HKD 11466.170015016\",\"date\":\"2022-01-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"6291D159-8A5C-4214-8A21-D1B0FCE98A61\"},{\"amountWithCurrency\":\"HKD 68.554765434840\",\"date\":\"2022-01-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"3F09F964-1E34-4481-83CA-CEEEF18E9DC3\"},{\"amountWithCurrency\":\"HKD 17048.545836480\",\"date\":\"2022-01-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"E3EA40BD-8E3D-4B1F-9D12-14FC19CF87D1\"},{\"amountWithCurrency\":\"HKD 36720.200842692\",\"date\":\"2022-01-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"CA963A53-13D7-41D5-A820-FCAD8D9325ED\"},{\"amountWithCurrency\":\"HKD 31046.160407592\",\"date\":\"2022-01-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"EB9CCD43-A424-41F1-A4EB-365BB1FF4A57\"},{\"amountWithCurrency\":\"HKD 37.428939959172\",\"date\":\"2022-01-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"22AE4E92-B3AE-451A-9E2F-162B2BE1E57D\"},{\"amountWithCurrency\":\"HKD 71.367441893472\",\"date\":\"2022-01-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"7107D26E-1CFF-4F97-BA01-3204276B0C5C\"},{\"amountWithCurrency\":\"HKD 41351.693526528\",\"date\":\"2022-01-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"C1F4DF58-3D01-4628-B966-A8384548C538\"},{\"amountWithCurrency\":\"HKD 2177.0477648448\",\"date\":\"2022-01-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A43FF41C-C087-4A68-855A-706367938AE5\"}]";
        testConnectionTemplate("3000",2022,1,1, expectedJson);
    }

    @Test
    public void testLastPageTransaction() throws Exception{
        String expectedJson ="[{\"amountWithCurrency\":\"HKD 26915.208448526\",\"date\":\"2022-01-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"7FD3C39A-AA42-4563-AEE5-13D12BF4B492\"},{\"amountWithCurrency\":\"HKD 14.621673572085\",\"date\":\"2022-01-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"C1911BAF-7F16-423B-9796-C2FE45BBFA42\"},{\"amountWithCurrency\":\"HKD 14392.728996747\",\"date\":\"2022-01-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"4E4E7BFC-BB96-46AD-8C74-1C74416F3753\"},{\"amountWithCurrency\":\"HKD 21254.793104121\",\"date\":\"2022-01-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"5B5B0042-50F4-41E1-9B47-A6EA31B698C6\"},{\"amountWithCurrency\":\"HKD 19525.621494732\",\"date\":\"2022-01-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"14A14B71-FEBE-49AA-923B-B43125064CB3\"}]";
        testConnectionTemplate("3000",2022,1,240, expectedJson);
    }

    @Test
    public void testLastPageTransaction2() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 8933.458886616\",\"date\":\"2022-02-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"7254DE30-CF0F-4545-B35E-35DA30A1EC3A\"},{\"amountWithCurrency\":\"HKD 22678.853739446\",\"date\":\"2022-02-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"4D50F333-5765-45CC-A6D1-54DA69CE240D\"},{\"amountWithCurrency\":\"HKD 23432.710927412\",\"date\":\"2022-02-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"D25F3C13-8FEB-412B-B388-D7D9E272ADFC\"}]";
        testConnectionTemplate("3000",2022,2,102, expectedJson);
    }

    @Test
    public void testLastPageTransaction3() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 53.849194162200\",\"date\":\"2022-03-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"BB022450-5EFF-45AB-B640-19E7BAC27C3B\"},{\"amountWithCurrency\":\"HKD 33543.630666267\",\"date\":\"2022-03-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"15B7A74A-42D2-49C2-81DF-79C4E0171B4C\"},{\"amountWithCurrency\":\"HKD 20613.754109865\",\"date\":\"2022-03-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"1BB09B6D-D36F-4927-88D2-9FFC6A6CACAE\"}]";
        testConnectionTemplate("3000",2022,3,105, expectedJson);
    }

    @Test
    public void testOutOfPageTransaction() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2022,1,241, expectedJson);
    }

    @Test
    public void testOutOfPageTransaction2() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2022,2,103, expectedJson);
    }

    @Test
    public void testOutOfPageTransaction3() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2022,3,106, expectedJson);
    }

    @Test
    public void testNegativePageTransaction() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2022,1,0, expectedJson);
    }

    @Test
    public void testNegativePageTransaction2() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2022,1,-1, expectedJson);
    }

    @Test
    public void testUserIDWithNoTransaction() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("5600",2022,1,1, expectedJson);
    }

    @Test
    public void testUserIDWithNoTransaction2() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("-10",2022,1,1, expectedJson);
    }

    @Test
    public void testYearWithNoTransaction() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2023,1,1, expectedJson);
    }

    @Test
    public void testYearWithNoTransaction2() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2021,1,1, expectedJson);
    }

    @Test
    public void testMonthWithNoTransaction() throws Exception{
        String expectedJson = "[]";
        testConnectionTemplate("3000",2021,7,1, expectedJson);
    }


    public void testConnectionTemplate(String userId, int year, int month, int pageNum, String expectedResult) throws Exception{
        String url = getAuthUrl(userId, "123");
        ResponseEntity<String> response = restTemplate.postForEntity(url,null, String.class);
        String accessToken = getAccessToken(response.getBody());
        String transactionUrl = getTransactionUrl(year,month,pageNum);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> transactionResponse = restTemplate.exchange(transactionUrl, HttpMethod.GET, entity, String.class);
        assertEquals(200, transactionResponse.getStatusCodeValue());
        System.out.println(transactionResponse.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Transaction> transactions = objectMapper.readValue(transactionResponse.getBody(), new TypeReference<List<Transaction>>() {});
        List<Transaction> transactions1 = objectMapper.readValue(expectedResult, new TypeReference<List<Transaction>>() {});
        assertTrue(checkSimilarity(transactions,transactions1));
    }


    public boolean checkSimilarity(List<Transaction> transactionList, List<Transaction> transactionList2) {
        if(transactionList.size() != transactionList2.size()) {
            return false;
        }
        for(int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);
            Transaction transaction1 = transactionList2.get(i);
            if(!compareTwoTransactions(transaction,transaction1)) {
                return false;
            }
        }
        return true;
    }

    public boolean compareTwoTransactions(Transaction transaction, Transaction transaction1) {
        return transaction.getDate().equals(transaction1.getDate()) &&
                transaction.getAccountIBAN().equals(transaction1.getAccountIBAN()) &&
                transaction.getDescription().equals(transaction1.getDescription()) &&
                transaction.getUID().equals(transaction1.getUID());
    }

    public String getAuthUrl(String userId, String password) {
        return "http://localhost:3002/auth/oauth/token?client_id=Synpulse&grant_type=password&username=" + userId + "&password=" + password;
    }

    public String getTransactionUrl(int year, int month, int pageNum) {
        return "http://localhost:3012/synpulse/transaction?year=" + year + "&month=" + month + "&pageNum=" + pageNum;
    }

    public String getAccessToken(String responseBody) throws Exception{
        JsonNode jsonNode = JsonUtils._string_to_json(responseBody);
        String accessToken = JsonUtils.json_to_string(jsonNode, "access_token");
        return accessToken;
    }
}
