package online.automationintesting.utils;

import java.util.HashMap;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestContext {
    private RequestSpecBuilder requestSpecBuilder;
    private Response response;

    private HashMap<String, Object> store;

    /**
     * Initializes the TestContext with:
     * - a new RequestSpecBuilder
     * - a null Response object
     * - an empty HashMap to store values
     * @return
     */
    public TestContext() {
      this.requestSpecBuilder = new RequestSpecBuilder().addHeader("Cache-Control", "no-cache").addHeader("Host", "automationintesting.online");
      this.response = null;
      this.store = new HashMap<String, Object>();
    }

    /**
     * Returns the ReastAssured RequestSpecBuilder object
     * @return RequestSpecBuilder
     */
    public RequestSpecBuilder getRequestSpecBuilder() {
        return this.requestSpecBuilder;
    }
    
    /**
     * Adds a Json body to the RequestSpecBuilder object and adds the "Content-Type: application/json" header
     * @param jsonBody Stringified JSON object to be added to the request body
     */
    public RequestSpecBuilder addJsonBody(String jsonBody) {
        return this.requestSpecBuilder
            .addHeader("Content-Type", "application/json")
            .setBody(jsonBody);
    }
    
    /**
     * Builds the RequestSpecification object from the RequestSpecBuilder
     * @return
     */
    public RequestSpecification getRequestSpecification() {
        return this.requestSpecBuilder.build();
    }
    
    /**
     * Sets the Response object
     * @param _response Response object value
     */
    public void setResponse(Response _response) {
        this.response = _response;
    }

    /**
     * Returns the Response object
     * @return Response object
     */
    public Response getResponse() {
        return this.response;
    }

    /**
     * Stores a key-value pair in the Test Context store
     * @param key Key to store
     * @param value Value to store
     */
    public void storeValue(String key, Object value) {
        this.store.put(key, value);
    }

    /**
     * Retrieves a value from the Test Context store
     * @param key Key to retrieve
     * @return Value stored in the key or null if the key does not exist
     */
    public Object getValue(String key) {
        return this.store.get(key);
    }

    public Object removeKeyValue(String key) {
        return this.store.remove(key);
    }
}