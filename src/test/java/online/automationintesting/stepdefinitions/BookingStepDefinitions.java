package online.automationintesting.stepdefinitions;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import online.automationintesting.pojo.Booking;
import online.automationintesting.pojo.BookingDates;
import online.automationintesting.pojo.Bookings;
import online.automationintesting.utils.TestContext;

public class BookingStepDefinitions {
    private TestContext testContext;

  public BookingStepDefinitions(TestContext testContext) {
    this.testContext = testContext;
  }

  @When("I book room {string} between the dates {string} and {string} with my info as booking {string}:")
  public void i_book_room_between_the_dates_and_with_my_info(String roomStoreId, String fromDate, String toDate, String bookingStoreId, io.cucumber.datatable.DataTable bookingDataTable) throws Exception{
    // Get the roomid from the test context store
    String roomid = this.testContext.getValue(roomStoreId).toString();

    // Extract the single row of data as a Map
    Map<String, String> bookingData = bookingDataTable.asMaps(String.class, String.class).get(0);

    // Create a new Booking object using the extracted data
    Booking booking = new Booking(
        0,                                              // Not used by the API, so set to 0
        Integer.parseInt(roomid),                                 // Parse room ID
        bookingData.get("firstname"),                         // First Name
        bookingData.get("lastname"),                          // Last Name
        Boolean.parseBoolean(bookingData.get("depositpaid")), // Deposit Paid
        bookingData.get("email"),                             // Email
        bookingData.get("phone"),                             // Phone
        new BookingDates(fromDate, toDate)                        // Create BookingDates object using provided dates
    );

    // convert the Booking object to a JSON string
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(booking);

    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/booking/")
    .addCookie("token", this.testContext.getValue("token").toString())
    .addHeader("Content-Type", "application/json")
    .setBody(jsonBody)
    .build();

    // call the createBooking endpoint to create the new booking
    Response response = given().spec(requestSpecification).when().post();

    // check status code
    ValidatableResponse validatableResponse = response.then().statusCode(201);

    // get the newly created booking information from the response body
    Booking newBooking = validatableResponse
    .extract()
    .jsonPath()
    .getObject("booking", Booking.class);

    // store the new booking information into the test context store
    this.testContext.storeValue(bookingStoreId, newBooking);
  }

  @When("I cancel the booking {string}")
  public void i_cancel_the_booking(String bookingStoreId) {
    // Retrieve the newly created booking information from the test context store
    Booking booking = (Booking) this.testContext.getValue(bookingStoreId);

    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/booking/" + booking.getBookingId())
    .addCookie("token", this.testContext.getValue("token").toString())
    .build();

    // call the getBooking endpoint to retrieve the newly created booking
    Response response = given().spec(requestSpecification).log().all().when().log().all().delete();

    // check status code
    response.then().log().all().statusCode(202);
  }

  @Then("I should be able to find information about the booking {string}")
  public void i_should_be_able_to_find_information_about_the_booking(String bookingStoreId) {
    // Retrieve the newly created booking information from the test context store
    Booking booking = (Booking) this.testContext.getValue(bookingStoreId);

    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/booking/" + booking.getBookingId())
    .addCookie("token", this.testContext.getValue("token").toString())
    .build();

    // call the getBooking endpoint to retrieve the newly created booking
    Response response = given().spec(requestSpecification).log().all().when().log().all().get();

    // check status code
    ValidatableResponse validatableResponse = response.then().log().all().statusCode(200);

    // get the newly created booking information from the response body
    Booking retrievedBooking = validatableResponse
    .extract()
    .response()
    .as(Booking.class);

    // check that the retrieved booking information matches the newly created booking information
    Assert.assertEquals(booking.getBookingId(), retrievedBooking.getBookingId());
  }

  @Then("I should see the following information for the booking {string}")
  public void i_should_see_the_following_information_for_the_booking(String bookingStoreId, io.cucumber.datatable.DataTable bookingDataTable) {
    // Retrieve the stored booking information from the test context store
    Booking booking = (Booking) this.testContext.getValue(bookingStoreId);

    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/booking/" + booking.getBookingId())
    .addCookie("token", this.testContext.getValue("token").toString())
    .build();

    // call the getBooking endpoint to retrieve the newly created booking
    Response response = given().spec(requestSpecification).log().all().when().log().all().get();

    // check status code
    ValidatableResponse validatableResponse = response.then().log().all().statusCode(200);

    // get the newly created booking information from the response body
    Booking retrievedBooking = validatableResponse
    .extract()
    .response()
    .as(Booking.class);

    // Extract the single row of data as a Map
    Map<String, String> bookingData = bookingDataTable.asMaps(String.class, String.class).get(0);

    // check that the retrieved booking information matches the newly created booking information
    Assert.assertEquals(booking.getBookingId(), retrievedBooking.getBookingId());
    Assert.assertEquals(booking.getRoomId(), retrievedBooking.getRoomId());
    Assert.assertEquals(bookingData.get("firstname"), retrievedBooking.getFirstname());
    Assert.assertEquals(bookingData.get("lastname"), retrievedBooking.getLastName());
    Assert.assertEquals(Boolean.parseBoolean(bookingData.get("depositpaid")), retrievedBooking.getDepositPaid());
  }

  @Then("I should be able to find in the summary the booking {string}")
  public void i_should_be_able_to_find_in_the_summary_the_booking(String bookingStoreId) {
    // Retrieve a stored booking information from the test context store
    Booking booking = (Booking) this.testContext.getValue(bookingStoreId);
    System.out.println(">>> ROOM ID :" + booking.getRoomId());

    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/booking/summary")
    .addQueryParam("roomid", booking.getRoomId())
    .addCookie("token", this.testContext.getValue("token").toString())
    .build();

    // call the getBooking endpoint to retrieve the newly created booking
    Response response = given().log().all().spec(requestSpecification).when().log().all().get();

    // check status code
    ValidatableResponse validatableResponse = response.then().log().all().statusCode(200);

    // get the summary information for the room where the booking was made
    Bookings bookings = validatableResponse.extract().as(Bookings.class);

    for (Bookings.BookingItem bookingItem : bookings.getBookings()) {
        BookingDates bookingDates = bookingItem.getBookingDates();
        System.out.println("Checkin: " + bookingDates.getCheckin());
        System.out.println("Checkout: " + bookingDates.getCheckout());
    }

    // check that the retrieved summary information contains the booking dates from our booking
    Assert.assertTrue(bookings.containsBookingDates(booking.getBookingDates()));
  }


  @Then("I should not be able to find in the summary the booking {string}")
  public void i_should_not_be_able_to_find_in_the_summary_the_booking(String bookingStoreId) {
    // Retrieve a stored booking information from the test context store
    Booking booking = (Booking) this.testContext.getValue(bookingStoreId);
    System.out.println(">>> ROOM ID :" + booking.getRoomId());

    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/booking/summary")
    .addQueryParam("roomid", booking.getRoomId())
    .addCookie("token", this.testContext.getValue("token").toString())
    .build();

    // call the getBooking endpoint to retrieve the newly created booking
    Response response = given().log().all().spec(requestSpecification).when().log().all().get();

    // check status code
    ValidatableResponse validatableResponse = response.then().log().all().statusCode(200);

    // get the summary information for the room where the booking was made
    Bookings bookings = validatableResponse.extract().as(Bookings.class);

    for (Bookings.BookingItem bookingItem : bookings.getBookings()) {
        BookingDates bookingDates = bookingItem.getBookingDates();
        System.out.println("Checkin: " + bookingDates.getCheckin());
        System.out.println("Checkout: " + bookingDates.getCheckout());
    }

    // check that the retrieved summary information contains the booking dates from our booking
    Assert.assertFalse(bookings.containsBookingDates(booking.getBookingDates()));
  }

  
}
