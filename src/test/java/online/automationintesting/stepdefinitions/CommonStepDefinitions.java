package online.automationintesting.stepdefinitions;

import java.io.File;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import online.automationintesting.utils.TestContext;
import org.junit.Assert;
import static io.restassured.RestAssured.given;

public class  CommonStepDefinitions{
  private TestContext testContext;

  // Dependency Injection: Sharing state between step definitions
  public CommonStepDefinitions(TestContext context) {
      this.testContext = context;
  }
  
  /* GIVEN VERB */

  /**
   * Sets the base URI for the API tests
   * @param baseURI Base URI to use when sending requests
   */
  @Given("I test APIs on the base URI {string}")
  public void i_test_ap_is_on_the_base_URI(String baseURI) {
    this.testContext.getRequestSpecBuilder().setBaseUri(baseURI);
  }

  /**
   * Sets the base path for the API tests
   * @param endpoint Endpoint to use when sending requests
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

  /**
   * Adds a cookie to the request header
   * @param keyStore Key to store the cookie value in the test context
   */
  @Given("I add a cookie to the header with key {string}")
  public void i_add_a_cookie_to_the_header_with_key(String keyStore) {
    // retrieve the cookie value from the test context
    String tokenValue = (String) this.testContext.getValue(keyStore);
    this.testContext.getRequestSpecBuilder().addCookie("token", tokenValue);
  }

  /**
   * Loads a JSON data file and stores it in the test context
   * @param key Key to store the json object in the test context
   * @param value Value of the JSON object to store in the test context
   */
  @Given("I load a json data file from {string} and store it to the test context as {string}")
  public void i_load_a_json_data_file_from_and_store_it_to_the_test_context_as(String path, String keyStore) throws Exception{
    // load the JSON file from the resources/data directory
    File jsonFile = new File("src/test/resources/data/" + path + ".json");

    // parse the JSON file
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(jsonFile);

    // store the jsonNode object representation of the JSON file in the test context
    this.testContext.storeValue(keyStore, jsonNode);
  }

  /**
   * Updates a field in a JSON object stored in the test context
   * @param keyStore Key to retrieve the JSON object from the test context
   * @param fieldName Field to update in the JSON object
   * @param newValue New value to set for the field
   */
  @Given("I update the {string} key in the test context with the field {string} set to {string}")
  public void i_update_the_key_in_the_test_context_with_the_field_set_to(String keyStore, String fieldName, String newValue) {
      // retrieve from the test context store the element to update
      JsonNode storedElement = (JsonNode) this.testContext.getValue(keyStore);
      System.out.println("--- i_update_the_key_in_the_test_context_with_the_field_set_to ---");

      System.out.println("--- storedElement ---" + storedElement.toString());

      // check if the newValue is a context variable or a litteral
      newValue = getParameterValue(newValue);

      System.out.println("--- newValue ---" + newValue);

      // update the field in the stored element
      ((ObjectNode) storedElement).put(fieldName, newValue);

      // store the updated element back in the test context
      this.testContext.storeValue(keyStore, storedElement);

      System.out.println("--- i_update_the_key_in_the_test_context_with_the_field_set_to ---" + storedElement.toString());
  }

  /**
   * Adds a JSON object stored in the test context as the request body
   * @param keyStore Key to retrieve the JSON object from the test context
   */
  @Given("I use the {string} key in the test context as the request body")
  public void i_use_the_key_in_the_test_context_as_the_request_body(String keyStore) {
    // retrive the value from the test context
    JsonNode jsonNode = (JsonNode) this.testContext.getValue(keyStore);

    System.out.println("--- I use the {string} key in the test context as the request body ---" + jsonNode.toString());

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
    // compute the path as it might be a variable
    path = getParameterValue(path);

    // send the request
    RequestSpecification requestSpecification = this.testContext.getRequestSpecification();
    Response response = given().spec(requestSpecification).when().log().all().request(method, path);

    // store the response in the test context
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

  /**
   * Verifies that the in context response has a non empty response body
   */
  @Then("I should receive an non-empty response body")
  public void i_should_receive_an_non_empty_response_body() {
    // extract the response body from the response
    String responseBody = this.testContext.getResponse().getBody().asString();
    Assert.assertNotEquals(responseBody,"");
  }
  // Helper methods

  /**
   * Helper method that checks if a parameter is a variable from the test context key store 
   * by checking if they are curly braces in the expression. The rest is considered a litteral.
   * @param parameter Parameter to check
   */
  private Boolean isContextVariable(String parameter) {
    return parameter.contains("{") && parameter.contains("}");
  }

  /**
   * Helper method that retrieves the value of a parameter from the test context if it is a variable or returns the litteral value
   * @param parameter Parameter to retrieve the value for
   */
  private String getParameterValue(String parameter) {
    System.out.println("--- getParameterValue ---" + parameter);
    // Determine if the parameter is a litteral or a variable from the test context
    if (isContextVariable(parameter)) {
      // Find the position of the opening and closing curly braces
      Integer openingCurlyBraceIndex = parameter.indexOf("{");
      Integer closingCurlyBraceIndex = parameter.indexOf("}");

      // get the value from the test context
      String variableName = parameter.substring(openingCurlyBraceIndex+1, closingCurlyBraceIndex);
      String variableValue = (String) this.testContext.getValue(variableName);

      // add the litteral parts from left and right of the variable
      parameter = parameter.substring(0, openingCurlyBraceIndex) + variableValue + parameter.substring(closingCurlyBraceIndex+1);
    }
    System.out.println("--- getParameterValue ---" + parameter);
    return parameter;
  }
}
