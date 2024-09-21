package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.bs.A;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.APIRunner;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.Map;

public class PojoTest {
    Faker faker = new Faker();

    @Test
    public void CreateCategory() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();
        requestBody.setCategory_title(faker.name().title());
        requestBody.setCategory_description(faker.name().title());
        requestBody.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int id = customResponse.getCategory_id();

        String url2 = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories/" + id;
        Response response2 = RestAssured.given().auth().oauth2(token).get(url2);
        CustomResponse customResponse1 = mapper.readValue(response.asString(), CustomResponse.class);
        int id2 = customResponse1.getCategory_id();
        Assert.assertEquals(id, id2);
    }

    @Test
    public void Create15Sellers() {
        for (int i = 0; i < 15; i++) {
            String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
            String token = CashWiseToken.GetToken();
            RequestBody requestBody = new RequestBody();
            requestBody.setCompany_name(faker.name().title());
            requestBody.setSeller_name(faker.name().title());
            requestBody.setAddress(faker.name().title());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setNumber(faker.number().digits(9));

            Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);
        }
    }

    @Test
    public void GetAllSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 1000);
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
//        response.prettyPrint();

        int status = response.statusCode();
        Assert.assertEquals(200, status);
        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            String email = customResponse.getResponses().get(i).getEmail();
            Assert.assertFalse(email.isEmpty());
        }
    }

    @Test
    public void CreateSeller2() {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("oles");
        requestBody.setSeller_name("oles");
        requestBody.setEmail("email2@gmail.com");
        requestBody.setAddress("address 123");
        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestBody).post(url);
        response.prettyPrint();
        System.out.println(response.getStatusCode());

    }

    @Test
    public void Archive5513() {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.GetToken();
        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", 5513);
        params.put("archive", true);
        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);

        response.prettyPrint();
        System.out.println(response.getStatusCode());

    }

    @Test
    public void ArchiveAllSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String UrlToArchive = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.GetToken();
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 1000);
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
//        response.prettyPrint();

        int status = response.statusCode();
        Assert.assertEquals(200, status);
        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            int id = customResponse.getResponses().get(i).getSeller_id();
            Map<String, Object> params1 = new HashMap<>();
            params1.put("sellersIdsForArchive", id);
            params1.put("archive", true);

            Response response1 = RestAssured.given().auth().oauth2(token).params(params1).post(UrlToArchive);


            int status1=response1.getStatusCode();
            Assert.assertEquals(status1,200);
        }
    }


    @Test
    public void ArchiveAllSellersHotMail() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String urlToArchive = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.GetToken();

        // Get all sellers with isArchived = false
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 1000);
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        // Validate the response status code
        int status = response.statusCode();
        Assert.assertEquals(200, status);

        // Map the response to a CustomResponse object
        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        // Loop through sellers and archive only those with email ending in @hotmail.com
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            int id = customResponse.getResponses().get(i).getSeller_id();
            String email = customResponse.getResponses().get(i).getEmail();

            if (email.endsWith("@hotmail.com")) {
                Map<String, Object> params1 = new HashMap<>();
                params1.put("sellersIdsForArchive", id);
                params1.put("archive", true);

                Response response1 = RestAssured.given()
                        .auth().oauth2(token)
                        .params(params1)
                        .post(urlToArchive);

                int status1 = response1.statusCode();
                Assert.assertEquals(200, status1);
//                System.out.println("Archived seller with ID: " + id + " and email: " + email);
            }
        }
    }
    @Test
    public void Create1SellerAndVerifyItWasCreated() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("CatWise");
        requestBody.setSeller_name("Meayw ");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setNumber("31274325234");
        requestBody.setAddress("Earth");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        int status = response.statusCode();

        Assert.assertEquals(201, status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int ExpectedSellerId = customResponse.getSeller_id();


        String url2 = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("size", 1000 );
        params.put("page", 1 );

        Response response1 = RestAssured.given().auth().oauth2(token).params(params).get(url2);
        int statusCode = response.statusCode();
        Assert.assertEquals(201, statusCode);

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);

        int size = customResponse1.getResponses().size();

        boolean isPresent = false;

        for(int i = 0; i < size; i ++ ){
            if(customResponse1.getResponses().get(i).getSeller_id() == ExpectedSellerId){
                isPresent = true;
                break;
            }
        }

        Assert.assertTrue(isPresent);

    }
    @Test
    public void testGet(){
//APIRunner.runGET("/api/myaccount/sellers/");
    }








}