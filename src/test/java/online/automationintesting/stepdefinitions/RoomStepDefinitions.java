package online.automationintesting.stepdefinitions;

import static io.restassured.RestAssured.given;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import online.automationintesting.pojo.Booking;
import online.automationintesting.pojo.BookingDates;
import online.automationintesting.pojo.Room;
import online.automationintesting.utils.TestContext;
import org.junit.Assert;

public class RoomStepDefinitions {
  
  private TestContext testContext;
  
  public RoomStepDefinitions(TestContext testContext) {
    this.testContext = testContext;
  }
  
  /**
   * Quick Step to create a new room and store the room id in the test context store
   * @param roomKey The key to store the room id in the test context store
   * @throws Exception
   */
  @Given("I have a new room available for booking with id {string}")
  public void i_have_a_new_room_available_for_booking_with_id(String roomKey) throws Exception{
    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // generate a random Room object
    Room randomRoom = Room.generateRandom();

    // convert the Room object to a JSON string
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(randomRoom);

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/room/")
    .addCookie("token", this.testContext.getValue("token").toString())
    .addHeader("Content-Type", "application/json")
    .setBody(jsonBody)
    .build();
    
    // call the createRoom endpoint to create the new room
    Response response = given().spec(requestSpecification).when().post();

    // check status code
    ValidatableResponse validatableResponse = response.then().statusCode(201);

    // get the newly created room's ID from the response body
    String roomId = validatableResponse.extract().jsonPath().get("roomid").toString();

    // store the room id into the test context store
    this.testContext.storeValue(roomKey,roomId);
  }

  /**
   * Quick Step to create a new booking for a given room and store the booking id in the test context store
   * @param bookingKey The key to store the booking id in the test context store
   * @param roomKey The key to retrieve the room id from the test context store
   * @throws Exception
   */
  @Given("I make a booking {string} for the room with id {string}")
  public void i_make_a_booking_for_the_room_with_id(String bookingKey, String roomKey) throws Exception {
    System.out.println(">>>>>>> roomKey/value: " + roomKey + "/" + this.testContext.getValue(roomKey));
    System.out.println(">>>>>>> token: " + this.testContext.getValue("token"));
    System.out.println(">>>>>>> bookingKey: " + bookingKey);
    // retrieve the room id from the test context store
    String roomId = this.testContext.getValue(roomKey).toString();

    // Instantiate a new Room object
    Booking booking = new Booking(
      0,
      Integer.parseInt(roomId),
      "John",
      "Doe",
      true,
      "john.doe@gmail.com",
      "+32/123456789",
      new BookingDates(
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
      )
      );
  
    // convert the Room object to a JSON string
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
    response.then().statusCode(201);

    // retrieve the booking id from the response body
    Integer bookingId = response.then().extract().jsonPath().get("bookingid");

    // store the booking id into the test context store
    this.testContext.storeValue(bookingKey, bookingId);
  }

  /**
   * Quick Step to delete a room by its id
   * @param roomKey The key to retrieve the room id from the test context store
   */
  @When("I delete the room with the id {string}")
  public void i_delete_the_room_with_the_id(String roomKey) {
    // Instantiate a new RequestSpecBuilder object to not pollute the Test Context
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

    // Retrieve the room id from the test context store
    String roomId = this.testContext.getValue(roomKey).toString();

    // add all necessary headers, the auth token and the JSON body to the RequestSpecBuilder object
    RequestSpecification requestSpecification = requestSpecBuilder
    .addHeader("Cache-Control", "no-cache")
    .addHeader("Host", "automationintesting.online")
    .setBaseUri("https://automationintesting.online/room/" + roomId)
    .addCookie("token", this.testContext.getValue("token").toString())
    .build();
    
    // call the createRoom endpoint to create the new room
    Response response = given().spec(requestSpecification).when().delete();

    // check status code
    response.then().statusCode(202);

    // delete the room id from the test context store
    Assert.assertNotNull(this.testContext.removeKeyValue(roomKey));
  }
}
