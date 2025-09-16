package org.desafioqa.integrationTest.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiConfig {
    private static final RequestSpecification DEFAULT_SPEC = new RequestSpecBuilder()
            .setBaseUri("https://demoqa.com")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.URI)
            .log(LogDetail.BODY)
            .build();

    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "https://demoqa.com";
    }

    private ApiConfig() {}

    public static RequestSpecification defaultSpec() {
        return DEFAULT_SPEC;
    }
}
