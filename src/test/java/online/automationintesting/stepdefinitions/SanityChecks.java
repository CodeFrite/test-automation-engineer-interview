package online.automationintesting.stepdefinitions;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class SanityChecks {

    private String endpoint;
    private Response response;

    @Given("I use the Binance API")
    public void i_use_the_binance_api() {
        // Set the base URI for Binance API
        io.restassured.RestAssured.baseURI = "https://api.binance.com";
    }

    @Given("I use the endpoint {string}")
    public void i_use_the_endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("I send a {string} request")
    public void i_send_a_request(String method) {
        response = given().when().request(method, endpoint);
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_a_status_code(int statusCode) {
        response.then().statusCode(statusCode);
    }
}