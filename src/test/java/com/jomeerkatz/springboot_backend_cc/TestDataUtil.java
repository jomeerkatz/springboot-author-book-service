package com.jomeerkatz.springboot_backend_cc;

import com.jomeerkatz.springboot_backend_cc.domain.dto.AuthorDto;
import com.jomeerkatz.springboot_backend_cc.domain.dto.BookDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
                .age(80)
                .name("testname")
                .build();
    }

    public static AuthorEntity createTestAuthorSecond() {
        return AuthorEntity.builder()
                .age(22)
                .name("testname-2")
                .build();
    }

    public static AuthorEntity createTestAuthorThird() {
        return AuthorEntity.builder()
                .age(33)
                .name("testname-3")
                .build();
    }

    public static BookEntity createTestBook(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("isbn-123")
                .title("book-title-test")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookSecond(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("isbn-234")
                .title("book-title-test-2")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookThird(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("isbn-345")
                .title("book-title-test-3")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("isbn-213")
                .title("book-title-test-123")
                .authorEntity(authorDto)
                .build();
    }
}
