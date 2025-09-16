package org.desafioqa.integrationTest.factory;

import org.desafioqa.integrationTest.dto.CreateUserRequest;
import org.desafioqa.utils.UsernameFactory;

public final class CreateUserRequestFactory {

    private static final String STRONG_PASSWORD = "SenhaForte123!";

    private CreateUserRequestFactory() {}

    
    public static CreateUserRequest valid(String usernamePrefix) {
        return new CreateUserRequest(UsernameFactory.unique(usernamePrefix), STRONG_PASSWORD);
    }

    
    public static CreateUserRequest weakPassword(String usernamePrefix) {
        return new CreateUserRequest(UsernameFactory.unique(usernamePrefix), "123");
    }

    
    public static CreateUserRequest sameUsername(CreateUserRequest original) {
        return new CreateUserRequest(original.getUserName(), original.getPassword());
    }
}
