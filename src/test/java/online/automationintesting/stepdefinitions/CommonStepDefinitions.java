package online.automationintesting.stepdefinitions;

import online.automationintesting.utils.TestContext;
import static io.restassured.RestAssured.given;

import java.io.File;

import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class  CommonStepDefinitions{
  private TestContext testContext;

  // Dependency Injection: Sharing state between step definitions
  public CommonStepDefinitions(TestContext context) {
      this.testContext = context;
  }
  
  /* GIVEN VERB */

  /**
   * Sets the base URI for the API tests
   * @param baseURI
   */
  @Given("I test APIs on the base URI {string}")
  public void i_test_ap_is_on_the_base_URI(String baseURI) {
    this.testContext.getRequestSpecBuilder().setBaseUri(baseURI);
  }

  /**
   * Sets the base path for the API tests
   * @param endpoint
   */
  @Given("I test the endpoint {string}")
  public void i_test_the_endpoint(String endpoint) {
    this.testContext.getRequestSpecBuilder().setBasePath(endpoint);
  }

  /**
   * Loads a JSON request body from a data file located in the 'resources/data' directory
   * @param path Relative path to the JSON file without the file extension
   */
  @Given("I use the json request body from file {string}")
  public void i_use_the_json_request_body_from_file(String path) throws Exception {
    // load the JSON file from the resources/data directory
    File jsonFile = new File("src/test/resources/data/" + path + ".json");

    // parse the JSON file
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(jsonFile);

    // add the JSON body to the request specification
    this.testContext.addJsonBody(jsonNode.toString());
  }

  /* WHEN VERB */

  /**
   * Sends the in context request with the given HTTP method
   * @param method HTTP method to use when sending the request
   */
  @When("I send a {string} request")
  public void i_send_a_request(String method) {
    RequestSpecification requestSpecification = this.testContext.getRequestSpecification();
    Response response = given().spec(requestSpecification).when().request(method);
    this.testContext.setResponse(response);
  }

  /**
   * Sends the in context request with the given HTTP method and path:
   * Ex: I base URI "https://api.example.com" and optinal base path "/v1" and send a GET request to "/users"
   * the request will be sent to "https://api.example.com/v1/users"
   * @param method HTTP method to use when sending the request
   * @param path Relative path to append to the in context base path
   */
  @When("I send a {string} request to {string}")
  public void i_send_a_request_to(String method, String path) {
    RequestSpecification requestSpecification = this.testContext.getRequestSpecification();
    Response response = given().spec(requestSpecification).when().request(method, path);
    this.testContext.setResponse(response);
  }

  /* THEN VERB */

  /**
   * Verifies that the in context response status code matches the expected status code
   * @param code Expected status code
   */
  @Then("the response should have a status code {int}")
  public void the_response_should_have_a_status_code(Integer code) {
    this.testContext.getResponse().then().statusCode(code);
  }

  /**
   * Verifies that the in context response has a cookie with the given key
   * @param key
   */
  @Then("the response should have a cookie with a {string} key")
  public void the_response_should_have_a_cookie_with_a_key(String key) {
    this.testContext.getResponse().then().cookie(key);
  }

  /**
   * Verifies that the in context response has a header with the given key/value pair
   * @param key
   * @param value
   */
  @Then("the response should have a json body with key value {string} \\/ {string}")
  public void the_response_should_have_a_json_body_with_key_value(String key, String value) {
    this.testContext.getResponse().then().assertThat().body(key, org.hamcrest.Matchers.equalTo(value));    
  }

  /**
   * Asserts that a key exists in the Test Context store
   * @param key Key to check for in the Test Context store
   */
  @Then("the context should have a key {string}")
  public void the_context_should_have_a_key(String key) {
    Assert.assertNotNull(this.testContext.getValue(key));
  }

  /**
   * Asserts that a key does not exist in the Test Context store
   * @param key Key to check for in the Test Context store
   */
  @Then("the context should not have a key {string}")
  public void the_context_should_not_have_a_key(String key) {
    Assert.assertNull(this.testContext.getValue(key));
  }

}
