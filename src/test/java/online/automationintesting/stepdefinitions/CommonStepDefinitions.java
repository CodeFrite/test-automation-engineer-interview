package online.automationintesting.stepdefinitions;

import online.automationintesting.utils.TestContext;
import static io.restassured.RestAssured.given;

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
  
  @Given("I test APIs on the base URI {string}")
  public void i_test_ap_is_on_the_base_URI(String baseURI) {
    this.testContext.getRequestSpecBuilder().setBaseUri(baseURI);
  }

  @Given("I test the endpoint {string}")
  public void i_test_the_endpoint(String endpoint) {
    this.testContext.getRequestSpecBuilder().setBasePath(endpoint);
  }

  @When("I send a {string} request")
  public void i_send_a_request(String method) {
    RequestSpecification requestSpecification = this.testContext.getRequestSpecification();
    given().spec(requestSpecification).when().log().all().request(method);
    Response response = given().spec(requestSpecification).when().request(method);
    this.testContext.setResponse(response);
  }

  @Then("the response should have a status code {int}")
  public void the_response_should_have_a_status_code(Integer code) {
    this.testContext.getResponse().then().statusCode(code);
  }

  @Then("the response should have a cookie with a {string} key")
  public void the_response_should_have_a_cookie_with_a_key(String key) {
    this.testContext.getResponse().then().cookie(key);
  }

  @Then("the response should have a json body with key value {string} \\/ {string}")
  public void the_response_should_have_a_json_body_with_key_value(String key, String value) {
    this.testContext.getResponse().then().body(key, org.hamcrest.Matchers.equalTo(value));
  }
}
