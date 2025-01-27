Feature: User Authentication
  Test the authentication endpoint:
    - Authenticate the user with valid credentials
    - Authenticate the user with invalid credentials
    - Authenticate the user with valid credentials and incorrect method
    - Authenticate the user without credentials

  Make sure the endpoint returns a session token within a cookie

  Background: I am using the hotel API
    Given I test APIs on the base URI "https://automationintesting.online"
      And I test the endpoint "/auth/login"

  # Positive Test Case

  Scenario: S.001: Authenticate the user with valid credentials
    Given I use the credentials "admin" / "password"
     When I send a "POST" request
     Then the response should have a status code 200
      And the response should have a cookie with a "token" key
   
  # Negative Test Cases
  
  Scenario: S.002: Authenticate the user with invalid credentials
    Given I use the credentials "bad" / "credentials"
     When I send a "POST" request
     Then I should get a response with status code 403

  Scenario: S.003: Authenticate the user with valid credentials and incorrect method
    Given I use the credentials "admin" / "password"
     When I send a "GET" request
     Then the response should have a status code 405
      And the response should have a json body with key value "error" / "Method Not Allowed"

  Scenario: S.004: Authenticate the user without credentials
     When I send a "POST" request
     Then the response should have a status code 415
      And the response should have a json body with key value "error" / "Unsupported Media Type"

  # Quick Steps: Since testing this endpoint is not required and I am just using it to be able to test the other endpoints, 
  # I will created a quick step to authenticate the user with valid credentials and store the token in the context
  Scenario: Quick Authentication
    Given I am authenticated
     Then the context should have a key "token"