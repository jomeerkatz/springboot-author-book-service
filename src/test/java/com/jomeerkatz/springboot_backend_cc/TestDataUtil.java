package com.jomeerkatz.springboot_backend_cc;

import com.jomeerkatz.springboot_backend_cc.domain.Author;
import com.jomeerkatz.springboot_backend_cc.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static Author createTestAuthor() {
        return Author.builder()
                .age(80)
                .name("testname")
                .build();
    }

    public static Author createTestAuthorSecond() {
        return Author.builder()
                .age(22)
                .name("testname-2")
                .build();
    }

    public static Author createTestAuthorThird() {
        return Author.builder()
                .age(33)
                .name("testname-3")
                .build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder()
                .isbn("isbn-123")
                .title("book-title-test")
                .author(author)
                .build();
    }

    public static Book createTestBookSecond(final Author author) {
        return Book.builder()
                .isbn("isbn-234")
                .title("book-title-test-2")
                .author(author)
                .build();
    }

    public static Book createTestBookThird(final Author author) {
        return Book.builder()
                .isbn("isbn-345")
                .title("book-title-test-3")
                .author(author)
                .build();
    }
}
