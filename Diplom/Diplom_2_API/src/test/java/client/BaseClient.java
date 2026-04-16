package client;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseClient {

    protected static final String BASE_URL = "https://stellarburgers.education-services.ru";

    protected RequestSpecification getBaseSpec() {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json");
    }
}