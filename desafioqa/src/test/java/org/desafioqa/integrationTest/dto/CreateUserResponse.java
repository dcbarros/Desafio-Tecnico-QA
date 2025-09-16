package org.desafioqa.integrationTest.dto;

import java.util.List;

public class CreateUserResponse {
    private String userID;
    private String username;
    private List<Book> books;

    public CreateUserResponse() {}
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}
