package org.desafioqa.integrationTest.dto;

import java.util.ArrayList;
import java.util.List;

public class AddBooksRequest {
    private String userId;
    private List<Isbn> collectionOfIsbns;

    public AddBooksRequest() {}

    public AddBooksRequest(String userId, List<Isbn> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public static AddBooksRequest of(String userId, List<String> isbns) {
        List<Isbn> items = new ArrayList<>();
        for (String s : isbns) items.add(new Isbn(s));
        return new AddBooksRequest(userId, items);
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<Isbn> getCollectionOfIsbns() { return collectionOfIsbns; }
    public void setCollectionOfIsbns(List<Isbn> collectionOfIsbns) { this.collectionOfIsbns = collectionOfIsbns; }

    public static class Isbn {
        private String isbn;
        public Isbn() {}
        public Isbn(String isbn) { this.isbn = isbn; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
    }
}
