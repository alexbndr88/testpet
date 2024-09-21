package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.Product;
import entities.RequestBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceSteps {
    int size1;
    private RequestSpecification request;
    private Response response;
    private String token = CashWiseToken.GetToken();
    Map<String, Object> params = new HashMap<>();
    private RequestBody requestBody=new RequestBody();
    private final String baseUrl = Config.getProperty("cashWiseApiUrl");
    @Given("the user is on base url {string}")
    public void the_user_is_on_base_url(String string) {
       request= RestAssured.given().baseUri(baseUrl);
    }
    @Given("the user have valid token")
    public void the_user_have_valid_token() {
        request.auth().oauth2(token).contentType(ContentType.JSON);
    }
    @Given("the user hits get API with endpoint {string} and with params {string} and {string} x")
    public void the_user_hits_get_api_with_endpoint_and_with_params_and_x(String endurl, String page, String size) throws JsonProcessingException {
        params.put("size", 10);
        params.put("page", 1);
        response=request.params(params).get(endurl);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int size1 = customResponse.getResponses().size();
        System.out.println(size1);

    }

    @Then("the user validates if response is more or equal to {int}")
    public void the_user_validates_if_response_is_more_or_equal_to(Integer int1) throws JsonProcessingException {
        Assert.assertEquals(size1,0);

    }







    @When("the user sends a POST request to create a product with the following details:")
    public void the_user_sends_a_post_request_to_create_a_product_with_the_following_details() {
        Product product=new Product();
        product.setProduct_title("product");
        product.setProduct_id(1);
        product.setProduct_price(10);
        product.setCount_of_products(1);
        product.setService_type_id(1);
        product.setCategory_id(1);
        requestBody.setInvoice_title("title");
        requestBody.setClient_id(3434);
        requestBody.setDate_of_creation("2023-04-02");
        requestBody.setProducts(Arrays.asList(product));
        requestBody.setSum(100);
    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer int1) {

    }
    @Then("the response should contain the product name {string}")
    public void the_response_should_contain_the_product_name(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }














    @Given("provide request body")
    public void provide_request_body() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("i hit POST endpoint {string}")
    public void i_hit_post_endpoint(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("verify status code is {int}")
    public void verify_status_code_is(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }








}
