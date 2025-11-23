package com.jomeerkatz.springboot_backend_cc.dao;

import com.jomeerkatz.springboot_backend_cc.dao.impl.BookDaoImpl;
import com.jomeerkatz.springboot_backend_cc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImpTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl bookDaoImplUnderTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql(){
        Book book = Book.builder()
                .isbn("isbn-123")
                .title("book-title-test")
                .author_id(1L)
                .build();

        bookDaoImplUnderTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("isbn-123"),
                eq("book-title-test"),
                eq(1L)
        );
    }

}
