package com.jomeerkatz.springboot_backend_cc.utils;

import com.jomeerkatz.springboot_backend_cc.domain.Author;
import com.jomeerkatz.springboot_backend_cc.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .age(80)
                .name("testname")
                .build();
    }

    public static Author createTestAuthorSecond() {
        return Author.builder()
                .id(2L)
                .age(22)
                .name("testname-2")
                .build();
    }

    public static Author createTestAuthorThird() {
        return Author.builder()
                .id(3L)
                .age(33)
                .name("testname-3")
                .build();
    }

    public static Book createTestBook() {
        return Book.builder()
                .isbn("isbn-123")
                .title("book-title-test")
                .author_id(1L)
                .build();
    }

    public static Book createTestBookSecond() {
        return Book.builder()
                .isbn("isbn-234")
                .title("book-title-test-2")
                .author_id(2L)
                .build();
    }

    public static Book createTestBookThird() {
        return Book.builder()
                .isbn("isbn-345")
                .title("book-title-test-3")
                .author_id(3L)
                .build();
    }
}
