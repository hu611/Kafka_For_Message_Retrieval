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
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 7.8324530414388\",\"date\":\"2022-02-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"36D73AE6-920F-423F-9EA9-7D50D59F8EA1\"},{\"amountWithCurrency\":\"HKD 16072.195101082\",\"date\":\"2022-02-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"56A0AD7D-712F-4448-B4CA-1AF5B30530DC\"},{\"amountWithCurrency\":\"HKD 21.064805651787\",\"date\":\"2022-02-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"D68036BC-B08F-4E7D-AE14-924AECA0DF61\"},{\"amountWithCurrency\":\"HKD 501.25250470827\",\"date\":\"2022-02-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"2E73666B-7285-4633-B15A-86F528617FB2\"},{\"amountWithCurrency\":\"HKD 3473.2711861683\",\"date\":\"2022-02-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"5AC56A04-31EB-4F01-8AAC-810293D499C2\"},{\"amountWithCurrency\":\"HKD 38503.008473440\",\"date\":\"2022-02-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"5F1C3986-B124-41D4-BAB8-CB7C8938F60A\"},{\"amountWithCurrency\":\"HKD 18245.272518971\",\"date\":\"2022-02-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"4CF8C778-A818-4019-8E91-16C50CF48EEE\"},{\"amountWithCurrency\":\"HKD 5349.5653829554\",\"date\":\"2022-02-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"B321DAF2-DDCF-4674-A88C-99D299695E38\"},{\"amountWithCurrency\":\"HKD 2106.1206697793\",\"date\":\"2022-02-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"3D5F9C71-687F-4B99-A9ED-4093BEA7E9EC\"},{\"amountWithCurrency\":\"HKD 23907.314097541\",\"date\":\"2022-02-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"15756937-21F2-4B1A-AC6D-468BEFA62FE4\"}]";
        testConnectionTemplate("1530",2022,2,1, expectedJson);
    }

    @Test
    public void testNormalTransaction2() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 21872.097085607\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"65D0EE87-315A-4020-B32D-F3518F8F68EC\"},{\"amountWithCurrency\":\"HKD 11926.038061781\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"EA9AA611-641B-442A-AC3B-D944DC8E2944\"},{\"amountWithCurrency\":\"HKD 21215.654699763\",\"date\":\"2022-05-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"9DD590B0-45F1-466E-9245-9DAA2650AA23\"},{\"amountWithCurrency\":\"HKD 28735.626428591\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"F4477378-7FA5-4D09-868D-B735000046D6\"},{\"amountWithCurrency\":\"HKD 23920.311267889\",\"date\":\"2022-05-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"B51057DF-93C7-46AC-A422-529C3922F1A3\"},{\"amountWithCurrency\":\"HKD 13667.505378527\",\"date\":\"2022-05-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"75451B58-CE50-4453-9C12-E15FE2402560\"},{\"amountWithCurrency\":\"HKD 27382.010367151\",\"date\":\"2022-05-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"230B98B6-AD2C-4F27-829F-726AF041162B\"},{\"amountWithCurrency\":\"HKD 11799.042744424\",\"date\":\"2022-05-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"EB55B908-A00E-4041-998A-D226947D6652\"},{\"amountWithCurrency\":\"HKD 16915.646641382\",\"date\":\"2022-05-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A7FACF0F-297F-4068-B8E2-8B872DC320BC\"},{\"amountWithCurrency\":\"HKD 34311.719527655\",\"date\":\"2022-05-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"75C3EDB0-68CD-42AE-944F-467F8395FEDE\"}]";
        testConnectionTemplate("1530",2022,5,4, expectedJson);
    }

    @Test
    public void testNormalTransaction3() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 41434.919371135\",\"date\":\"2022-03-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"4C893DC1-820F-4968-8668-C7F9E39E4E85\"},{\"amountWithCurrency\":\"HKD 39346.826731801\",\"date\":\"2022-03-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"32734CF4-F49C-412A-BDDB-A6439F975842\"},{\"amountWithCurrency\":\"HKD 17591.687122672\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"722BC16E-7C29-44DB-BFE3-4616996A4402\"},{\"amountWithCurrency\":\"HKD 4765.7672864974\",\"date\":\"2022-03-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"B693B6EB-89B3-40B5-9FB8-76EBC5CBF63E\"},{\"amountWithCurrency\":\"HKD 2845.8541084361\",\"date\":\"2022-03-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"0DECF55C-B10A-4414-991E-29716A8C22E5\"},{\"amountWithCurrency\":\"HKD 382.93109289316\",\"date\":\"2022-03-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"2BF5AC33-C76E-496A-911B-4BA25A0A073F\"},{\"amountWithCurrency\":\"HKD 36194.245372981\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"1ABA6398-9E14-44D7-8A3D-118B52294906\"},{\"amountWithCurrency\":\"HKD 69.718928243441\",\"date\":\"2022-03-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"30CDEFFF-E1B6-4C92-9B1A-E9192FD6C77F\"},{\"amountWithCurrency\":\"HKD 31243.653889405\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"72079198-6995-46FA-9DA2-6C4916031D5B\"},{\"amountWithCurrency\":\"HKD 13657.203159511\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"2D39A84E-DBC5-4A5E-B465-6849A59CF28E\"}]";
        testConnectionTemplate("42",2022,3,4, expectedJson);
    }

    @Test
    public void testNormalTransaction4() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 3849.7482117544\",\"date\":\"2022-03-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"DA343034-0AB1-455D-A8B2-04CB91A635B3\"},{\"amountWithCurrency\":\"HKD 21443.770390359\",\"date\":\"2022-03-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"E53725F8-BF38-47E1-BEAF-90C23A2BBA7E\"},{\"amountWithCurrency\":\"HKD 24481.731034299\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"75BCD31E-9387-4AA5-B370-1C64DCE3113C\"},{\"amountWithCurrency\":\"HKD 26917.276243940\",\"date\":\"2022-03-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"FC1C3E77-2A6D-4705-B809-03106855E5A2\"},{\"amountWithCurrency\":\"HKD 30617.060648061\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"B95011FC-281F-4845-9578-DC475AD2D7EA\"},{\"amountWithCurrency\":\"HKD 3694.0149909055\",\"date\":\"2022-03-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"0DB490CE-3D5A-48B8-A85E-810A649B9192\"},{\"amountWithCurrency\":\"HKD 19.361681693233\",\"date\":\"2022-03-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"3B719E26-BDDF-48AB-8101-98D05C77072E\"},{\"amountWithCurrency\":\"HKD 2472.9171913877\",\"date\":\"2022-03-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A33E8E5C-5143-4A78-9CE2-6A19562859B1\"},{\"amountWithCurrency\":\"HKD 47385.747158579\",\"date\":\"2022-03-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"7E0094F0-CE5B-4068-8F42-387E9D4DBCE4\"},{\"amountWithCurrency\":\"HKD 42967.110071628\",\"date\":\"2022-03-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"83B1D257-6C96-4A76-95E7-D0B39E6DFE47\"}]";
        testConnectionTemplate("425",2022,3,36, expectedJson);
    }

    @Test
    public void testFirstPageTransaction() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 1644.9889118931\",\"date\":\"2022-01-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"FACC1675-24DB-4163-BB07-989A969315B8\"},{\"amountWithCurrency\":\"HKD 7245.9531709872\",\"date\":\"2022-01-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"4FB797D6-47A4-4702-9C7A-F1A6836A0FC6\"},{\"amountWithCurrency\":\"HKD 34748.881569675\",\"date\":\"2022-01-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"64E5DAA6-F606-42EF-9C10-A04D96E6DABA\"},{\"amountWithCurrency\":\"HKD 28912.776837035\",\"date\":\"2022-01-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"AB6A42CF-D87B-4931-A225-24D73DD85FD3\"},{\"amountWithCurrency\":\"HKD 7859.3914679337\",\"date\":\"2022-01-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A2028487-3441-41C7-B33A-0DCB9D3A030D\"},{\"amountWithCurrency\":\"HKD 11524.319746773\",\"date\":\"2022-01-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"D55FC986-0481-4283-A1EE-B1452D3E6612\"},{\"amountWithCurrency\":\"HKD 36460.644723480\",\"date\":\"2022-01-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A94CB224-2B16-4E64-8C86-231E81FA4754\"},{\"amountWithCurrency\":\"HKD 19768.167343034\",\"date\":\"2022-01-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"515C6D7A-8B0E-480D-A8C9-73B514241CE2\"},{\"amountWithCurrency\":\"HKD 38089.214047400\",\"date\":\"2022-01-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"E784EC1B-9021-4543-9E77-34FEF4DA05B5\"},{\"amountWithCurrency\":\"HKD 8029.5810558803\",\"date\":\"2022-01-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"08B554A9-5733-4C88-85DD-27A6099E5EEB\"}]";
        testConnectionTemplate("3000",2022,1,1, expectedJson);
    }

    @Test
    public void testLastPageTransaction() throws Exception{
        String expectedJson ="[{\"amountWithCurrency\":\"HKD 24170.933213438\",\"date\":\"2022-01-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"3E24130B-13D9-4291-BA24-033AA9F1C83B\"},{\"amountWithCurrency\":\"HKD 45796.484893802\",\"date\":\"2022-01-01\",\"description\":\"Online payment KYD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"57E513B0-BEDC-4311-9201-F23D5B8465B7\"},{\"amountWithCurrency\":\"HKD 19112.825111373\",\"date\":\"2022-01-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"6051AEFC-4DD2-42AF-A72A-51D0E221A864\"},{\"amountWithCurrency\":\"HKD 9.981639204070\",\"date\":\"2022-01-01\",\"description\":\"Online payment KZT\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"A6532E1B-6658-4518-B495-935F403E1656\"},{\"amountWithCurrency\":\"HKD 3781.9821254124\",\"date\":\"2022-01-01\",\"description\":\"Online payment CNY\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"5F28463F-C82F-4A13-9ECE-7A3B12984705\"}]";
        testConnectionTemplate("3000",2022,1,240, expectedJson);
    }

    @Test
    public void testLastPageTransaction2() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 42039.765378629\",\"date\":\"2022-02-01\",\"description\":\"Online payment GBP\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"EE582E1C-9214-40B2-9CFF-796EDDFCA6BA\"},{\"amountWithCurrency\":\"HKD 2406.4380295900\",\"date\":\"2022-02-01\",\"description\":\"Online payment CHF\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"320010D9-1ACA-45D6-B4FE-3685CB4873FB\"},{\"amountWithCurrency\":\"HKD 24742.714897153\",\"date\":\"2022-02-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"DB14B83E-11E9-4D4B-8E76-E08EA6F91862\"}]";
        testConnectionTemplate("3000",2022,2,102, expectedJson);
    }

    @Test
    public void testLastPageTransaction3() throws Exception{
        String expectedJson = "[{\"amountWithCurrency\":\"HKD 11568.530593941\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"F267D159-083C-4827-BBB9-E692F551E98E\"},{\"amountWithCurrency\":\"HKD 1503.8306018872\",\"date\":\"2022-03-01\",\"description\":\"Online payment USD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"52E6DBBE-7A8F-419E-90CE-C52F6F5649B8\"},{\"amountWithCurrency\":\"HKD 20007.566010251\",\"date\":\"2022-03-01\",\"description\":\"Online payment CAD\",\"accountIBAN\":\"CH93-0000-0000-0000-0000-0\",\"uid\":\"CE7A5E26-33A1-4711-A13D-76802F633F11\"}]";
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
