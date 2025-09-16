package org.desafioqa.integrationTest.factory;

import org.desafioqa.integrationTest.dto.AuthRequest;
import org.desafioqa.integrationTest.dto.CreateUserRequest;

public final class AuthRequestFactory {
    private AuthRequestFactory() {}

    public static AuthRequest from(CreateUserRequest createUserRequest) {
        return new AuthRequest(createUserRequest.getUserName(), createUserRequest.getPassword());
    }
}
