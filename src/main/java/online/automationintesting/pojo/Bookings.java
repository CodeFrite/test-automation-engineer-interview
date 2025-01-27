package online.automationintesting.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Bookings {

    @JsonProperty("bookings")
    private List<BookingItem> bookings;

    public Bookings() {
    }

    public Bookings(List<BookingItem> bookings) {
        this.bookings = bookings;
    }

    public List<BookingItem> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingItem> bookings) {
        this.bookings = bookings;
    }

    public Boolean containsBookingDates(BookingDates bookingDates) {
        for (BookingItem bookingItem : bookings) {
          BookingDates currentBookingDates = bookingItem.getBookingDates();
            if (currentBookingDates.equals(bookingDates)) {
                return true;
            }
        }
        return false;
    }

    public static class BookingItem {

        @JsonProperty("bookingDates")
        private BookingDates bookingDates;

        public BookingItem() {
        }

        public BookingItem(BookingDates bookingDates) {
            this.bookingDates = bookingDates;
        }

        public BookingDates getBookingDates() {
            return bookingDates;
        }

        public void setBookingDates(BookingDates bookingDates) {
            this.bookingDates = bookingDates;
        }
    }
}