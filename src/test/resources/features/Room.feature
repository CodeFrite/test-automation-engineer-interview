Feature: Use the Room endpoint to create a new Room for each test run to avoid having failing test cases due to double booking or deleted room

  Background:
    Given I am authenticated

  # Quick Step to add a new room and save its room id to the Test Context store
  Scenario: Quick Room Creation
    Given I have a new room available for booking with id "room-1" 
     Then the context should have a key "room-1"

  Scenario: Quick Room Deletion
    Given I have a new room available for booking with id "room-1"
     When I delete the room with the id "room-1"
     Then the context should not have a key "room-1"