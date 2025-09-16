package org.desafioqa.integrationTest.api;

import org.desafioqa.integrationTest.config.ApiConfig;
import org.desafioqa.integrationTest.dto.AddBooksRequest;
import org.desafioqa.integrationTest.dto.AuthRequest;
import org.desafioqa.integrationTest.dto.CreateUserRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DemoQaClient {

    public Response createUser(CreateUserRequest body) {
        return given().spec(ApiConfig.defaultSpec())
                .body(body)
                .post("/Account/v1/User");
    }

    public Response generateToken(AuthRequest body) {
        return given().spec(ApiConfig.defaultSpec())
                .body(body)
                .post("/Account/v1/GenerateToken");
    }

    public Response authorized(AuthRequest body) {
        return given().spec(ApiConfig.defaultSpec())
                .body(body)
                .post("/Account/v1/Authorized");
    }

    public Response getUser(String token, String userId) {
        return given().spec(ApiConfig.defaultSpec())
                .header("Authorization", "Bearer " + token)
                .get("/Account/v1/User/{userId}", userId);
    }

    public Response listBooks() {
        return given().spec(ApiConfig.defaultSpec())
                .get("/BookStore/v1/Books");
    }

    public Response addBooks(String token, AddBooksRequest body) {
        return given().spec(ApiConfig.defaultSpec())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .post("/BookStore/v1/Books");
    }
}
