package online.automationintesting.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestContext {
    private RequestSpecBuilder requestSpecBuilder;
    private Response response;

    public TestContext() {
      this.requestSpecBuilder = new RequestSpecBuilder().addHeader("Cache-Control", "no-cache").addHeader("Host", "automationintesting.online");
      this.response = null;
    }

    public RequestSpecBuilder getRequestSpecBuilder() {
        return this.requestSpecBuilder;
    }

    public RequestSpecification getRequestSpecification() {
        return this.requestSpecBuilder.build();
    }

    public void setResponse(Response _response) {
        this.response = _response;
    }

    public Response getResponse() {
        return this.response;
    }
}