Feature: Non-Functional Testing of the `Booking` endpoint

  Background: Set the base URL and path for the booking endpoint
    Given I test APIs on the base URI "https://automationintesting.online"
      And I test the endpoint "/booking"

  # getBooking operation
  Scenario: NF.001: (auth) Get an existing booking and observe 200 (OK) + booking object in the response body
    Given I am authenticated
      And I add a cookie to the header with key "token"
      And I have a new room available for booking with id "new-room"
      And I make a booking "new-booking" for the room with id "new-room"
     When I send a "GET" request to "/{new-room}"
     Then the response should have a status code 200
      And I should receive an non-empty response body


  #Scenario: NF.002: (no auth) Get an existing booking and observe 403 (Forbidden) + error message "Forbidden"
  Scenario: NF.003: (no auth) Get a non-existing booking and observe 403 (Forbidden) + error message "Forbidden"
    Given I send a "GET" request to "/1"
     Then the response should have a status code 403
  
  Scenario: NF.004: (auth) Get a non-existing booking and observe 404 (Not Found) + error message "Not Found"
    Given I am authenticated
      And I add a cookie to the header with key "token"
     When I send a "GET" request to "/123456789"
     Then the response should have a status code 404

  #Scenario: NF.005: (auth) No booking id in path and observe equivalent to operation `getBookings`

  Scenario: NF.006: (auth) Get a booking with id 0 and observe 404 (Not Found)
   Given I am authenticated
      And I add a cookie to the header with key "token"
     When I send a "GET" request to "/0"
     Then the response should have a status code 404

  Scenario: NF.007: (auth) Get a booking with id -1 and observe 404 (Not Found)
   Given I am authenticated
      And I add a cookie to the header with key "token"
     When I send a "GET" request to "/-1"
     Then the response should have a status code 404

  Scenario: NF.008: (auth) Get a booking with id abc and observe 404 (Not Found)
   Given I am authenticated
      And I add a cookie to the header with key "token"
     When I send a "GET" request to "/abc"
     Then the response should have a status code 404

  Scenario: NF.009: (auth) Get a booking with id 1000000000 and observe 404 (Bad Request)
   Given I am authenticated
      And I add a cookie to the header with key "token"
     When I send a "GET" request to "/1000000000"
     Then the response should have a status code 404

  # updateBooking operation
  #Scenario: NF.101: (auth) Update an existing booking with valid data and observe 200 (OK) + booking changed with new data
  #Scenario: NF.102: (no auth) Update an existing booking with valid data and observe 403 (Forbidden)
  #Scenario: NF.103: (auth) Update an existing booking with invalid data and observe 400 (Bad Request)

  # deleteBooking operation
  #Scenario: NF.201: (auth) Delete an existing booking and observe 202 (Accepted)
  #Scenario: NF.202: (no auth) Delete an existing booking and observe 403 (Forbidden)
  #Scenario: NF.203: (auth) Delete a non-existing booking and observe 404 (Not Found)

  # createBooking operation
  #Scenario: NF.301: (auth) Create a booking with valid data and observe 201 (Created) + booking object in the response body
  #Scenario: NF.302: (no auth) Create a booking with valid data and observe 403 (Forbidden)
  #Scenario: NF.303: (auth) Create a booking for a room already booked for that single date and observe 409 (Conflict)
  #Scenario: NF.304: (auth) Create a booking for a room already booked for that period and observe 409 (Conflict)
  #Scenario: NF.305: (auth) Create a booking with invalid data and observe 400 (Bad Request)
  #Scenario: NF.306: (auth) Create a booking for a room already booked for that period with invalid data and observe 400 (Bad Request)

  # getBookings operation
  #Scenario: NF.401: (auth) Get all bookings for an existing room and observe 200 (OK) + list of `Booking` objects in the response body
  #Scenario: NF.402: (no auth) Get all bookings for an existing room and observe 403 (Forbidden)
  #Scenario: NF.403: (auth) Get all bookings for a non-existing room and observe 404 (Not Found)

  # getSummaries operation
  #Scenario: NF.501: (auth) Get summaries of all bookings for a room that has been booked and observe 200 (OK) + list of `BookingDates` objects in the response body
  #Scenario: NF.502: (auth) Get summaries of all bookings for a room that has not been booked and observe 200 (OK) + empty list