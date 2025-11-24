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

    public static Book createTestBook() {
        return Book.builder()
                .isbn("isbn-123")
                .title("book-title-test")
                .author_id(1L)
                .build();
    }
}
