package org.desafioqa.integrationTest.dto;

import java.util.List;

public class ListBooksResponse {
    private List<Book> books;

    public ListBooksResponse() {}
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}
