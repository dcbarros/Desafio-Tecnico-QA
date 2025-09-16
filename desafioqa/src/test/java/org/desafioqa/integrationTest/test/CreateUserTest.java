package org.desafioqa.integrationTest.test;

import org.desafioqa.integrationTest.api.DemoQaClient;
import org.desafioqa.integrationTest.dto.CreateUserRequest;
import org.desafioqa.integrationTest.dto.CreateUserResponse;
import org.desafioqa.integrationTest.factory.CreateUserRequestFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class CreateUserTest {

    private static CreateUserRequest validOnce;

    @Test
    @Order(1)
    @DisplayName("Criação de usuário: deve retornar 201 e conter userID/username")
    void givenValidUser_whenCreateUser_then201() {
        DemoQaClient client = new DemoQaClient();

        CreateUserRequest req = CreateUserRequestFactory.valid("qa");

        Response resp = client.createUser(req);
        resp.then().statusCode(201);

        CreateUserResponse body = resp.as(CreateUserResponse.class);
        assertNotNull(body.getUserID(), "userID não deve ser nulo");
        assertEquals(req.getUserName(), body.getUsername(), "username deve bater com o enviado");
        assertNotNull(body.getBooks(), "books deve existir (pode vir vazio)");

        validOnce = req;
    }

    @Test
    @Order(2)
    @DisplayName("Senha fraca: deve retornar 400 e mensagem apropriada")
    void givenWeakPassword_whenCreateUser_then400() {
        DemoQaClient client = new DemoQaClient();

        CreateUserRequest req = CreateUserRequestFactory.weakPassword("qa");

        Response resp = client.createUser(req);
        resp.then().statusCode(400);

        String message = resp.jsonPath().getString("message");
        assertNotNull(message, "deve haver campo 'message'");
        assertTrue(message.toLowerCase().contains("password"),
                "mensagem deve indicar regra de senha (foi: " + message + ")");
    }

    @Test
    @Order(3)
    @DisplayName("Username já existente: deve retornar 406 (User exists!)")
    void givenExistingUsername_whenCreateUser_then406() {
        DemoQaClient client = new DemoQaClient();

        CreateUserRequest duplicated = CreateUserRequestFactory.sameUsername(validOnce);

        Response resp = client.createUser(duplicated);
        resp.then().statusCode(406);

        String message = resp.jsonPath().getString("message");
        assertEquals("User exists!", message, "mensagem de conflito deve ser 'User exists!'");
    }
    
}
