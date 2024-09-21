package steps;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.it.Ma;
import org.junit.Assert;
import utilities.APIRunner;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class SellerSteps {
    Faker faker = new Faker();
    String email;
    String sellername;
    int sellerid;

    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endPoint) {
        APIRunner.runGET(endPoint, 5602);
        sellerid = APIRunner.getCustomResponse().getSeller_id();
    }

    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
        String email = APIRunner.getCustomResponse().getEmail();
        Assert.assertFalse(email.isEmpty());

    }

    @Given("user hit get all seller api with {string}")
    public void user_hit_get_all_seller_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 10);
        params.put("isArchived", false);
        APIRunner.runGET(endpoint, params);

    }

    @Then("verify seller ids are not equal to {int}")
    public void verify_seller_ids_are_not_equal_to(int int1) {
        int size = APIRunner.getCustomResponse().getResponses().size();
        for (int i = 0; i < size; i++) {
            int sellerid = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals(int1, sellerid);
        }
    }


    @Then("user hits put api with {string}")
    public void user_hits_put_api_with(String endpoint) {
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().firstName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setNumber(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());
        APIRunner.runPUT(endpoint, requestBody, 5609);
        email = APIRunner.getCustomResponse().getEmail();
        sellername = APIRunner.getCustomResponse().getSeller_name();

    }

    @Then("verify user email was updated")
    public void verify_user_email_was_updated() {
        Assert.assertFalse(email.isEmpty());

    }

    @Then("verify user first name was updated")
    public void verify_user_first_name_was_updated() {
        Assert.assertFalse(sellername.isEmpty());
    }


    @Then("user hits post api with {string}")
    public void user_hits_post_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", sellerid);
        params.put("archive", true);
        APIRunner.runPOST(endpoint, params);


    }

    @Then("verify user was archived")
    public void verify_user_was_archived(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 100);
        params.put("isArchived", true);
        APIRunner.runGET(endpoint, params);

        boolean isPresent = false;
        int size = APIRunner.getCustomResponse().getResponses().size();
        for (int i = 0; i < size; i++) {
            int ids = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if (sellerid == ids) {
                isPresent = true;
                break;
            }
        }
        Assert.assertTrue(isPresent);
    }


    @Then("verify seller id was created {string}")
    public void verify_seller_id_was_created(String endpoint)  {
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("whatever");
        requestBody.setSeller_name("name");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setNumber(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());
        APIRunner.runPOST(endpoint, requestBody);
        sellerid = APIRunner.getCustomResponse().getSeller_id();
        sellername = APIRunner.getCustomResponse().getSeller_name();
    }

    @Then("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertTrue(sellerid != 0);
    }

    @Then("delete the seller with {string}")
    public void delete_the_seller_with(String endpoint) {
        APIRunner.runDELETE(endpoint+sellerid);
Assert.assertFalse(sellername.isEmpty());
    }

    @Then("user hits get all sellers with {string}")
    public void user_hits_get_all_sellers_with(String endpoint) {
APIRunner.runDELETE(endpoint+sellerid);
    }

    @Then("verify deleted seller is not in the list")
    public void verify_deleted_seller_is_not_in_the_list() {
       boolean isEmpty=true;
       int size=APIRunner.getCustomResponse().getResponses().size();
       for (int i=0;i<size;i++){
           int id=APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
           if (id!=sellerid){
               isEmpty=false;
               break;
           }
       }
       Assert.assertFalse(isEmpty);
    }


}
