package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    @Test
    public void testToken() {
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requesBody = new RequestBody();
        requesBody.setEmail("olesmakarenko@gmail.com");
        requesBody.setPassword("12345678");
        Response response = RestAssured.given().contentType(ContentType.JSON).body(requesBody).post(endPoint);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
        response.prettyPrint();
        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);
    }

    @Test
    public void GetSingleSeller() {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/" + 4695;
        String token = CashWiseToken.GetToken();
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        String expectedEmail = response.jsonPath().getString("email");
        Assert.assertFalse(expectedEmail.isEmpty());
        Assert.assertTrue(expectedEmail.endsWith(".com"));
    }

    @Test
    public void GetAllSellers() {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 10);
        params.put("page", 1);
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int StatusCode = response.statusCode();
        Assert.assertEquals(StatusCode, 200);
//        response.prettyPrint();
        String email = response.jsonPath().getString("responses[0].email");
        String email3 = response.jsonPath().getString("responses[2].email");
        String email5 = response.jsonPath().getString("responses[4].email");
        Assert.assertFalse(email.isEmpty());
//        Assert.assertFalse(email3.isEmpty());
//        Assert.assertFalse(email5.isEmpty());


        List<String> listOfEmails = response.jsonPath().getList("responses.email");
        for (String emails : listOfEmails) {
            Assert.assertFalse(emails.isEmpty());
        }
    }

@Test
    public void getallResponses() throws JsonProcessingException {
    String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/invoices/seller";
    String token = CashWiseToken.GetToken();
    Map<String, Object> params = new HashMap<>();
    params.put("size", 10);
    params.put("page", 1);
    Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
    ObjectMapper mapper = new ObjectMapper();
    CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
    int size = customResponse.getResponses().size();
    Assert.assertEquals(size,0);

}
@Test
    public void createproduct(){
    String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/products/post";
    String token = CashWiseToken.GetToken();
   RequestBody requestBody=new RequestBody();
   requestBody.setProduct_title("product");
   requestBody.setProduct_price(10);
   requestBody.setService_type_id(10);
   requestBody.setCategory_id(10);
   requestBody.setProduct_description("product good");
   requestBody.setDate_of_payment("2024-08-23");
    requestBody.setRemind_before_day(10);
    requestBody.setDo_remind_every_month("REPEAT_EVERY_MONTH");

    Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);
    response.prettyPrint();


}


}