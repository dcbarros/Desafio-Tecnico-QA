package org.desafioqa.integrationTest.test;

import java.util.List;
import java.util.stream.Collectors;

import org.desafioqa.integrationTest.api.DemoQaClient;
import org.desafioqa.integrationTest.dto.*;
import org.desafioqa.integrationTest.factory.CreateUserRequestFactory;
import org.desafioqa.integrationTest.factory.AuthRequestFactory;

import org.junit.jupiter.api.*;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookStoreFlowTest {

    private static final DemoQaClient client = new DemoQaClient();

    private static CreateUserRequest userReq;
    private static String userId;
    private static String token;
    private static List<String> pickedIsbns;

    @BeforeAll
    static void bootstrap() {
        
        userReq = CreateUserRequestFactory.valid("qa");
        Response rCreate = client.createUser(userReq);
        rCreate.then().statusCode(201);
        userId = rCreate.as(CreateUserResponse.class).getUserID();

    
        Response rToken = client.generateToken(AuthRequestFactory.from(userReq));
        rToken.then().statusCode(200);
        token = rToken.as(GenerateTokenResponse.class).getToken();
        assertNotNull(token, "token não pode ser nulo");
    }

    @Test
    @Order(1)
    @DisplayName("Listar livros: deve retornar 200 e pelo menos 2 itens")
    void whenListBooks_thenReturnAllBooks() {
        Response r = client.listBooks();
        r.then().statusCode(200);

        ListBooksResponse body = r.as(ListBooksResponse.class);
        assertNotNull(body.getBooks(), "lista de livros não pode ser nula");
        assertTrue(body.getBooks().size() >= 2, "precisa de pelo menos 2 livros para o fluxo");
        
        // escolhe os 2 primeiros isbns para usar nos próximos testes
        pickedIsbns = body.getBooks().stream()
                .limit(2)
                .map(Book::getIsbn)
                .collect(Collectors.toList());
    }

    @Test
    @Order(2)
    @DisplayName("Adicionar 2 livros ao usuário: 201")
    void givenValidUser_whenRentBooks_thenReturn201() {
        // Valida pré-condição
        assertNotNull(pickedIsbns, "pré-condição: selecionamos 2 isbns");

        AddBooksRequest req = AddBooksRequest.of(userId, pickedIsbns);

        Response r = client.addBooks(token, req);
        r.then().statusCode(201);
    }

    @Test
    @Order(3)
    @DisplayName("Detalhes do usuário devem conter os 2 livros adicionados")
    void givenUserId_whenGetUserDetails_thenReturnBooks() {
        Response r = client.getUser(token, userId);
        r.then().statusCode(200);


        java.util.List<String> userIsbns = r.jsonPath().getList("books.isbn");
        assertNotNull(userIsbns, "books não deve ser nulo");
        for (String isbn : pickedIsbns) {
            assertTrue(userIsbns.contains(isbn), "user deve conter isbn: " + isbn);
        }
    }
}
