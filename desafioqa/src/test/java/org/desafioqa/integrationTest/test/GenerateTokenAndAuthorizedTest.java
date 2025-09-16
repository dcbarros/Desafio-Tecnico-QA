package org.desafioqa.integrationTest.test;

import org.desafioqa.integrationTest.api.DemoQaClient;
import org.desafioqa.integrationTest.dto.*;
import org.desafioqa.integrationTest.factory.CreateUserRequestFactory;
import org.desafioqa.integrationTest.factory.AuthRequestFactory;

import org.junit.jupiter.api.*;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenerateTokenAndAuthorizedTest {

    private static final DemoQaClient client = new DemoQaClient();

    private static CreateUserRequest userReq;
    private static String userId;

    @BeforeAll
    static void createUser() {
        userReq = CreateUserRequestFactory.valid("qa");
        Response r = client.createUser(userReq);
        r.then().statusCode(201);
        userId = r.as(CreateUserResponse.class).getUserID();
        assertNotNull(userId, "userId não pode ser nulo");
    }

    @Test
    @Order(1)
    @DisplayName("Gerar token: 200 + token não nulo")
    void givenValidUser_whenGenerateToken_thenReturn200() {
        Response r = client.generateToken(AuthRequestFactory.from(userReq));
        r.then().statusCode(200);

        GenerateTokenResponse body = r.as(GenerateTokenResponse.class);
        assertNotNull(body.getToken(), "token não pode ser nulo");
        assertEquals("Success", body.getStatus(), "status deve ser 'Success'");

    }

    @Test
    @Order(2)
    @DisplayName("Validação autorizada: deve retornar true")
    void givenValidUser_whenAuthorizedVerification_thenReturnTrue() {
        Response r = client.authorized(AuthRequestFactory.from(userReq));
        r.then().statusCode(200);

        String body = r.getBody().asString().trim().toLowerCase();
        assertTrue(body.contains("true"), "body deve indicar true (foi: " + body + ")");
    }

}
