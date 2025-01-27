package online.automationintesting.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import online.automationintesting.pojo.Credentials;
import online.automationintesting.utils.TestContext;
import static io.restassured.RestAssured.given;

public class AuthStepDefinitions {
  private TestContext testContext;

  public AuthStepDefinitions(TestContext context) {
      this.testContext = context;
  }

  /**
   * Authenticate the user without polluting the test context RequestSpecification object. But still stores the authentication token in the test context.
   */
  @Given("I am authenticated")
  public void i_am_authenticated() {
    RequestSpecification requestSpecification = new RequestSpecBuilder()
      .addHeader("Cache-Control", "no-cache")
      .addHeader("Host", "automationintesting.online")
      .addHeader("Content-Type", "application/json")
      .setBaseUri("https://automationintesting.online/auth/login")
      .setBody("{\"username\":\"admin\",\"password\":\"password\"}")
      .build();

    Response response = given().spec(requestSpecification).when().post();

    // check status code
    response.then().statusCode(200);

    // store the authentication token in the test context
    this.testContext.storeValue("token",response.getCookie("token"));
  }

  /**
   * Authenticate the user using the provided username and password
   * @param username
   * @param password
   * @throws JsonProcessingException
   */
  @Given("I use the credentials {string} \\/ {string}")
  public void i_use_the_credentials(String username, String password) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Credentials credentials = new Credentials(username, password);
    String jsonBody = objectMapper.writeValueAsString(credentials);

    this.testContext.getRequestSpecBuilder()
      .addHeader("Content-Type", "application/json")
      .setBody(jsonBody);
  }
  
  /**
   * Check the in context response status code
   * @param code the expected status code
   */
  @Then("I should get a response with status code {int}")
  public void i_should_get_a_response_with_status_code(Integer code) {
    this.testContext.getResponse().then().statusCode(code);
  }
}
