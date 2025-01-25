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

## 1. Test Plan

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

### 1.9 Reporting

Here is a table listing all the bugs classified by severity:

%% TODO: ADD TABLE

See `Github Action` results for the details

## 2. Test Project Setup

### 2.1 Installing Java

The first step is to install the JDK. Cucumber is compatible with all versions above 11 as we can see in the [cucumber-jvm](https://github.com/cucumber/cucumber-jvm/blob/main/pom.xml) pom file. :

```
<requireJavaVersion>
    <version>[11,)</version>
</requireJavaVersion>
```

Here I'll be using the latest version of the JDK installer LTS version for mac os, which is today `jdk-23` found at [oracle.com](https://www.oracle.com/java/technologies/downloads/#jdk23-mac). At the end of the installation, we are granted with success message:

<img src="docs/java-install.png" alt="java installation success message" width="500">

We can finally make sure the installation process as well as the integration into VSCode went well by checking the version of the JDK:

```bash
% java -version

java version "23.0.2" 2025-01-21
Java(TM) SE Runtime Environment (build 23.0.2+7-58)
Java HotSpot(TM) 64-Bit Server VM (build 23.0.2+7-58, mixed mode, sharing)
```

### 2.2 Maven

#### 2.2.1 Installation

The next step is to install Maven (build and dependencies management):

```bash
% brew install maven
```

This step is pretty verbose and takes a few minutes to complete. At the end, we can check the installation by asking for the Maven version:

```bash
% mvn -version

Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
Java version: 23.0.1, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/23.0.1/libexec/openjdk.jdk/Contents/Home
Default locale: en_BE, platform encoding: UTF-8
OS name: "mac os x", version: "14.5", arch: "aarch64", family: "mac"
```

#### 2.2.2 Initializing the Maven Project

We can now initialize the Maven project. We could do this from the command line using an archetype for a quickstart project. However, for the sake of the exercise, I thought it would be more interesting to do it from scratch. Therefore, I'll manually create a folder structure that will suits our use case and add a pom file.

My pom file skeleton will already define my group id set to `online.automationintesting` to match the domain of the website we are testing. The artifact id will be `interview` to reveal the purpose of the project. The version will be set to `1.0-SNAPSHOT` as we are just starting the project.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <groupId>online.automationintesting</groupId>
  <artifactId>interview</artifactId>
  <version>1.0-SNAPSHOT</version>

</project>
```

I haven't included a build section yet since I will be running my tests using a JUnit runner. If needed for the GitHub Actions, I will add it later.

#### 2.2.3 Adding Dependencies

We are now ready to add our dependencies. We need the following libraries:

- Cucumber for the BDD approach
- Cucumber JUnit for the test runner
- RestAssured for the API testing
- Jackson for the JSON de/serialization

If needed, I will also make use of a library to compare json files to check the API's response to ease a bit my task. But for now, let's head to [mvn repository website](https://mvnrepository.com/) and add the above dependencies to our pom file:

```xml
<dependencies>
  <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
  <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.18.2</version>
  </dependency>
  <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
  <dependency>
      <groupId>io.cucumber</groupId>
      <artifactId>cucumber-java</artifactId>
      <version>7.20.1</version>
  </dependency>
  <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
  <dependency>
      <groupId>io.cucumber</groupId>
      <artifactId>cucumber-junit</artifactId>
      <version>7.20.1</version>
      <scope>test</scope>
  </dependency>
  <!-- https://mvnrepository.com/artifact/io.rest-assured/rest-assured -->
  <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>5.5.0</version>
      <scope>test</scope>
  </dependency>
</dependencies>
```

Finally, we can run the following command to download the dependencies:

```bash
% mvn run install
...
[INFO] BUILD SUCCESS
```

## 3. Project Structure

I would normally update the structure of the project as I go whenever a new need appears, but since the dependencies are already known and since I would like to deliver of first working increment at the end of this sprint, I'll already define the structure of the project as follow:

```
bnppf_interview/
├── docs/                                    # Documentation for the project, images, other than readme.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── online/
│   │   │       └── automationintesting/
│   │   │           ├── pojos/               # POJO classes for request/response mapping
│   │   │           └── config/              # Configuration classes to setup the test environment
│   │   └── resources/
│   │       └── application.properties       # Base URL, API keys, or other test configurations
│   ├── test/
│   │   ├── java/
│   │   │   └── online/
│   │   │       └── automationintesting/
│   │   │           ├── hooks/               # Cucumber hooks for setup and teardown
│   │   │           ├── runners/             # Test runner classes for Cucumber
│   │   │           ├── stepdefinitions/     # Step definition classes for Cucumber
│   │   │           └── utils/               # Utility classes for testing (e.g., JSON serialization & comparison, ...)
│   │   └── resources/
│   │       ├── features/                    # Cucumber feature files
│   │       ├── data/                        # Test data in JSON format
│   │       ├── schemas/                     # JSON schemas for validation
│   │       └── logs/                        # Logs generated during test execution
│   └── test-reports/                        # Test reports (e.g., HTML, JSON, or XML)
├── pom.xml                                  # Maven build configuration and dependencies
└── readme.md                                # Documentation for the project
```

## 4. Junit Runner for Cucumber

Now that the project structure is set up, we can start writing the JUnit runner for Cucumber. This class will be responsible for running the Cucumber tests. It solely needs to know where the feature files and step definitions are located. I also added a few plugins to generate a pretty report in HTML format and a JSON report for further processing inside the `test-reports` folder:

```java
package online.automationintesting.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "online.automationintesting.stepdefinitions",
    plugin = {"pretty", "html:src/test-reports/cucumber-report.html", "json:src/test-reports/cucumber-report.json"})
public class JUnitCucumberRunner {
}
```

## 5. Sanity Checks

Now let's write some sanity check to make sure that:

- Cucumber is correctly installed
- RestAssured is correctly installed
- Junit runner is correctly set up to run feature files, find the step definitions and generate html & json reports

### 5.1 Creating a Feature File to ping Binance API

Let's create a feature file to ping the Binance API and check if the response status code is 200. This will be our first test to make sure that the setup is correct:

```gherkin
Feature: Sanity checks

  Scenario: Ping Binance API
   Given I use the Binance API
     And I use the endpoint "/api/v1/ping"
    When I send a "GET" request
    Then I should receive a 200 status code
```

### 5.2 Adding the Step Definitions

Now let's add the step definitions for the feature file. We will use RestAssured to send the request and check the status code:

```java
package online.automationintesting.stepdefinitions;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class SanityChecks {

    private String endpoint;
    private Response response;

    @Given("I use the Binance API")
    public void i_use_the_binance_api() {
        // Set the base URI for Binance API
        io.restassured.RestAssured.baseURI = "https://api.binance.com";
    }

    @Given("I use the endpoint {string}")
    public void i_use_the_endpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("I send a {string} request")
    public void i_send_a_request(String method) {
        response = given().when().request(method, endpoint);
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_a_status_code(int statusCode) {
        response.then().statusCode(statusCode);
    }
}
```

### 5.3 Running the Tests

The test runs correctly, JUnit runner is able to find the feature file, the step definitions and generate the HTML and JSON reports. The test passes as expected:

<img src="docs/sanity-checks.png" alt="sanity checks" width="350">

## 6. Deliverables

Now that I have completed the first sprint, I can commit my changes to Github with the basic project setup, folder structure, the JUnit runner and a sanity check. I will tag this version as `v1.0.0`.

The first sprint is now completed. We are now ready to move on to the crispy part: `start coding the Booking endpoint test suites`.
