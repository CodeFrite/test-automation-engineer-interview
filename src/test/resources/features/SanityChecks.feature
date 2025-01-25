Feature: Sanity checks

  Scenario: Ping Binance API
   Given I use the Binance API
     And I use the endpoint "/api/v1/ping"
    When I send a "GET" request
    Then I should receive a 200 status code