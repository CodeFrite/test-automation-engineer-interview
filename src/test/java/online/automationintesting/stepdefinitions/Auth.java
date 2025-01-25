package online.automationintesting.stepdefinitions;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import online.automationintesting.pojo.Credentials;
import online.automationintesting.utils.TestContext;

public class Auth {
  private TestContext testContext;

  public Auth(TestContext context) {
      this.testContext = context;
  }

  @Given("I use the credentials {string} \\/ {string}")
  public void i_use_the_credentials(String username, String password) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Credentials credentials = new Credentials(username, password);
    String jsonBody = objectMapper.writeValueAsString(credentials);

    this.testContext.getRequestSpecBuilder()
      .addHeader("Content-Type", "application/json")
      .setBody(jsonBody);
  }
  
  @Then("I should get a response with status code {int}")
  public void i_should_get_a_response_with_status_code(Integer code) {
    this.testContext.getResponse().then().statusCode(code);
  }
}
