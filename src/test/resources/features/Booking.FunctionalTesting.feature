Feature: Room Booking API
  Test the booking endpoint:

  Background: I am authenticated and I have a room available for booking
    Given I am authenticated
      And I have a new room available for booking with id "room-1"

  # Positive Test Case

  Scenario: FP.001: Booking a room and check its information as a single booking and in the summary
    When I book room "room-1" between the dates "2025-02-01" and "2025-02-02" with my info as booking "booking-1":
      | firstname | lastname | depositpaid | email                | phone             |
      | John      | Doe      | true        | john.doe@example.com | +32/12345.678.90  |
     Then I should be able to find information about the booking "booking-1" 
      And I should be able to find in the summary the booking "booking-1"
  
  Scenario: FP.002: When 3 guests book the same room for 3 different periods, the summary should show 3 bookings for the room
    When I book room "room-1" between the dates "2025-02-01" and "2025-02-02" with my info as booking "booking-1":
      | firstname | lastname | depositpaid | email                  | phone             |
      | client    | one      | true        | client.one@example.com | +32/12345.678.90  |
     And I book room "room-1" between the dates "2025-02-02" and "2025-02-03" with my info as booking "booking-2":
      | firstname | lastname | depositpaid | email                  | phone             |
      | client    | two      | true        | client.two@example.com | +32/12345.678.91  |
     And I book room "room-1" between the dates "2025-02-03" and "2025-02-04" with my info as booking "booking-3":
      | firstname | lastname | depositpaid | email                  | phone             |
      | client      | three    | true        | client.three@example.com | +32/12345.678.92  |
    Then I should be able to find in the summary the booking "booking-1"
     And I should be able to find in the summary the booking "booking-2"
     And I should be able to find in the summary the booking "booking-3"

  Scenario: FP.003: When a booking is cancelled, it should not appear in the summary
    When I book room "room-1" between the dates "2025-02-01" and "2025-02-02" with my info as booking "booking-1":
      | firstname | lastname | depositpaid | email                | phone             |
      | John      | Doe      | true        | john.doe@example.com | +32/12345.678.90  |
     And I cancel the booking "booking-1"
    Then I should not be able to find in the summary the booking "booking-1"
  
  Scenario: FP.004: When 2 bookings are made for the same room and the first one is cancelled, the summary should show only the second booking
    When I book room "room-1" between the dates "2025-02-01" and "2025-02-02" with my info as booking "booking-1":
      | firstname | lastname | depositpaid | email                  | phone             |
      | client    | one      | true        | client.one@example.com | +32/12345.678.90  |
     And I book room "room-1" between the dates "2025-02-02" and "2025-02-03" with my info as booking "booking-2":
      | firstname | lastname | depositpaid | email                  | phone             |
      | client    | two      | true        | client.two@example.com | +32/12345.678.90  |
     And I cancel the booking "booking-1"
    Then I should not be able to find in the summary the booking "booking-1"
     And I should be able to find in the summary the booking "booking-2"

  Scenario: FP.005: When a booking is made and its information are changed, I should see the changes in the booking information
    When I book room "room-1" between the dates "2025-02-01" and "2025-02-02" with my info as booking "booking-1":
      | firstname | lastname | depositpaid | email                | phone             |
      | John      | Doe      | true        | john.doe@example.com | +32/12345.678.90  |
    Then I should see the following information for the booking "booking-1" 
      | firstname | lastname | depositpaid | email                | phone             |
      | John      | Doe      | true        | john.doe@example.com | +32/12345.678.91  |
    When I update the "firstname" with the value "Matt" for booking "booking-1" 
    Then I should see the following information for the booking "booking-1" 
      | firstname | lastname | depositpaid | email                | phone             |
      | Matt      | Doe      | true        | john.doe@example.com | +32/12345.678.91  |
    When I update the "lastname" with the value "Damon" for booking "booking-1" 
    Then I should see the following information for the booking "booking-1" 
      | firstname | lastname | depositpaid | email                | phone             |
      | Matt      | Damon      | true      | john.doe@example.com | +32/12345.678.91  |  
    When I update the "depositpaid" with the value "false" for booking "booking-1" 
    Then I should see the following information for the booking "booking-1" 
      | firstname | lastname | depositpaid | email                | phone             |
      | Matt      | Damon    | false       | john.doe@example.com | +32/12345.678.91  |  
    
  # Negative Test Cases
