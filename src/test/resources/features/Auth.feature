Feature: User Authentication
  Test the authentication endpoint:
    - check if the enpoint is up and running
    - check if a bad username/password stops us from being authenticated
    - check if the default username/password credentials are loaded into the system
    - check if auth returns a token in the cookies

  Background: I am using the hotel API
    Given I test APIs on the base URI "https://automationintesting.online"
      And I test the endpoint "/auth/login"

  # Positive Test Case

  Scenario: Authenticate the user with valid credentials
    Given I use the credentials "admin" / "password"
     When I send a "POST" request
     Then the response should have a status code 200
      And the response should have a cookie with a "token" key
   
  # Negative Test Cases
  
  Scenario: Authenticate the user with invalid credentials
    Given I use the credentials "bad" / "credentials"
     When I send a "POST" request
     Then I should get a response with status code 403

  Scenario: Authenticate the user with valid credentials and incorrect method
    Given I use the credentials "admin" / "password"
     When I send a "GET" request
     Then the response should have a status code 405
      And the response should have a json body with key value "error" / "Method Not Allowed"

  Scenario: Authenticate the user without credentials
     When I send a "POST" request
     Then the response should have a status code 415
      And the response should have a json body with key value "error" / "Bad Request"
