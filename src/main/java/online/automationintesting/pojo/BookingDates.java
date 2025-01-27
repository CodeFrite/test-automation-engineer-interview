package online.automationintesting.pojo;

public class BookingDates {
  private String checkin;
  private String checkout;

  // Default constructor is required for Jackson to be able to deserialize JSON to a BookingDates object
  public BookingDates() {
  }

  public BookingDates(String checkin, String checkout) {
    this.checkin = checkin;
    this.checkout = checkout;
  }

  public String getCheckin() {
    return checkin;
  }

  public void setCheckin(String checkin) {
    this.checkin = checkin;
  }

  public String getCheckout() {
    return checkout;
  }

  public void setCheckout(String checkout) {
    this.checkout = checkout;
  }

  // Override the equals method to compare two BookingDates objects
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    BookingDates bookingDates = (BookingDates) obj;
    return checkin.equals(bookingDates.checkin) && checkout.equals(bookingDates.checkout);
  }
}
