# Interview BNPPF : Test Automation Engineer Position - Exercise

Today is a great day as I am going to code a solution for an exercise proposed by BNPPF Testing Team Lead,for a Test Automation Engineer position. It is currently 25 Jan 2025 9AM and I have 3 days to complete the exercise. I am excited to start coding and learning a few new things along the way. So let's get started!

## Exercise

The goal of the exercise is to create a Test Automation Framework to automate the testing of the APIs used on a hotel booking website located here: https://automationintesting.online/

There are two endpoints which are documented using OpenAPI specification (Swagger):

- `Booking:` https://automationintesting.online/booking/swagger-ui/index.html
- `Message:` https://automationintesting.online/message/swagger-ui/index.html

## Requirements

I am required to use the following tools:

- `Java` as the programming language
- `RestAssured` for API testing
- `Cucumber` for the BDD approach
- `Github` for the version control and to document the progress
- `Design Patterns` to structure the code in a maintainable way
- `Coverage` is left at my discretion but should be wide enough to demonstrate different testing techniques and types of tests

# General Approach

To mimic what would happen in a real setup where I am just one of the many members of the development team, I'll assume that:

- the OpenAPI documentation comes from the Dev Team and I can propose changes in the code exposed to me: here their OpenAPI documentation
- that I have to work with the BAs to define the User Stories in Gherkin language so that they can be used as the basis for the test automation code

Since Scrum is the preferred Dev Methodology for the position, I'll split my work into several sprints, propose some deliverable at the end of each iteration. Finally, I'll also make sure to use the ISTQB jargon as it was a requirement for the position and of course the preferred medium of expression among a team of testers.

# Tools & Methodology

- Operating System: `macOS Sonoma 14.5` on `Apple M2`
- Development Methodology: `Scrum`
- Behavior Driven Development: `Cucumber`
- Programming Language: `Java`
- Java Serialization Library: `Jackson`
- API Testing Library: `RestAssured`
- Test Runner & Assertion Library: `JUnit`
- User Story Definition Language: `Gherkin` through `Cucumber`
- API Requests Prototyping: `Postman` (as a VSCode plugin)
- API Documentation: `OpenAPI`
- Build & Dependencies Manager Tool: `Maven`
- CI/CD: `Github Actions` to launch automated test suites on each push to the main branch
- IDE: `Visual Studio Code`
- Version Control and Project Documentation: `Github`

# Sprint Planning

This process would normally take place over the course of several `Sprint Planning` sessions. Since the exercise is quite concise and I am working alone, I'll already shape the whole agenda in this section. I'll try to split the sprints in a logical way. Here is the general planning for the next sprints:

- Sprint 1: `Test Plan`, `Testing Project Setup` and `Boiler Plate Project Release`
- Sprint 2: `Booking` endpoint testing
- Sprint 3: `Messages`endpoint testing
- Sprint 4: `CI/CD Pipeline Integration` and `Reporting`

# Test Strategy

Let's assume that BNPPF has an overarching `Test Strategy`that gives general guidelines that must be followed by all the testing teams. Due to the nature of the exercise and the AUT, we could imagine having the following `guideline`:

- Before approval from the `Delivery Team`, all new projects should be accompanied by a `Test Plan` that will highlight the approach taken by the Testing Team, and will include:
  - the testing approach: test objectives and test techniques
  - clear test coverage objectives
  - a test results by severity levels
- Moreover, all the critical aspects of the application should be covered by automated test cases integrated in the CD/CI pipeline.
- All endpoints that require authentication should be covered and tested with both valid and invalid credentials.

This is of course a very light of a `Test Strategy` but it will be enough for the purpose of this exercise.

# Sprint 1

## 1 Test Plan

Here is a simple `Test Plan` for the exercise that should suit all our basic needs:

### 1.1 Introduction

Please refer to [Exercise section](#exercise)

### 1.2 Scope

- Testing both endpoints "Booking" and "Message"
- Testing the OpenAPI documentation as well

### 1.3 Out of Scope

Testing the UI of the website including:

- booking reservations online
- managing rooms through the UI admin panel

### 1.4 Test Approach

#### Types of Testing

We will perform the following types of testing:

- `Functional Testing`: to ensure that the API business logic is well observerd (e.g., all required fields are present, the API returns the correct status code, ...)
- `Integration Testing`: to ensure that the API is well integrated with the whole system database (writes) and other operations from the same endpoint (deleting a room that does not exist should return a 404, possibility to retrieve a booking that was booked beforehand, ...)
- `Boundary Testing`: to ensure that the API can handle the maximum and minimum values for each parameter
- `Negative Testing`: to ensure that the API can handle incorrect data types, missing parameters, and incorrect parameter locations

`Performance Testing` is out of the scope of this exercise.

#### Test Scenarios Definitions

All test scenarios will be described using the Gherkin language. Each of the two endpoints will be covered by three separate features files. Here is an example for the `Booking` endpoint:

| Feature File                      | Features                     | Description                                                                            |
| --------------------------------- | ---------------------------- | -------------------------------------------------------------------------------------- |
| BookingUnitTesting.feature        | Positive                     | Testing the Booking operations in isolation with valid data                            |
|                                   | Negative                     | Testing the Booking operations in isolation with invalid/missing data                  |
| BookingIntegrationTesting.feature | Positive                     | Testing the Booking operations in integration with valid data                          |
|                                   | Negative                     | Testing the Booking operations in integration with invalid/missing data                |
| BookingDataTesting.feature        | Boundary values              | Testing all parameters with boundary values                                            |
|                                   | Incorrect type               | Testing all parameters with incorrect data types                                       |
|                                   | Incomplete parameter list    | Testing all parameters with missing values                                             |
|                                   | Incorrect parameter location | Testing the API with parameters passed in the wrong section (header, body, query, ...) |

### 1.5 Test Coverage

We will cover all the operations for both endpoints. We will also cover the OpenAPI documentation to ensure that it is up to date and that it matches the actual API behavior.

### 1.6 Test Environment

Please refer to [Tools & Methodology section](#tools--methodology)

### 1.7 Test Deliverables

Here are the deliverables that will be produced:

- `Testing Framework Code` on Github
- `Github Action`to trigger the test suites on each push to the main branch
- `Test Report`

### 1.8 Test Schedule

| Date          | Sprint 1 | Sprint 2 | Sprint 3 | Sprint 4 |
| ------------- | -------- | -------- | -------- | -------- |
| `25-01-2015>` | X        | X        |          |          |
| `26-01-2015>` |          | X        | X        |          |
| `27-01-2015>` |          |          |          | X        |

## 1.9 Reporting

Here is a table listing all the bugs classified by severity:

%% TODO: ADD TABLE

See `Github Action` results for the details
