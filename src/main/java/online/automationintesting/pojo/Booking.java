package online.automationintesting.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Booking {
  @JsonProperty("bookingid")
  private Integer bookingId;

  @JsonProperty("roomid")
  private Integer roomId;

  @JsonProperty("firstname")
  private String firstName;

  @JsonProperty("lastname")
  private String lastName;

  @JsonProperty("depositpaid")
  private Boolean depositPaid;

  @JsonProperty("email")
  private String email;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("bookingdates")
  private BookingDates bookingDates;

  // Default constructor is required for Jackson to be able to deserialize JSON to a Booking object
  public Booking() {}

  // Constructor with all fields
  public Booking (Integer bookingId, Integer roomId, String firstName, String lastName, Boolean depositPaid, String email, String phone, BookingDates bookingDates) {
    this.bookingId = bookingId;
    this.roomId = roomId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.depositPaid = depositPaid;
    this.email = email;
    this.phone = phone;
    this.bookingDates = bookingDates;
  }

  // Getters & Setters
  public Integer getBookingId() {
    return bookingId;
  }

  public void setBookingId(Integer bookingId) {
    this.bookingId = bookingId;
  }

  public Integer getRoomId() {
    return roomId;
  }

  public void setRoomId(Integer roomId) {
    this.roomId = roomId;
  }

  public String getFirstname() {
    return firstName;
  }

  public void setFirstname(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Boolean getDepositPaid() {
    return depositPaid;
  }

  public void setDepositPaid(Boolean depositPaid) {
    this.depositPaid = depositPaid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public BookingDates getBookingDates() {
    return bookingDates;
  }

  public void setBookingDates(BookingDates bookingDates) {
    this.bookingDates = bookingDates;
  }

}
