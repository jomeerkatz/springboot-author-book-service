package com.jomeerkatz.springboot_backend_cc.dao.impl.JUnitTests;

import com.jomeerkatz.springboot_backend_cc.dao.impl.BookDaoImpl;
import com.jomeerkatz.springboot_backend_cc.domain.Book;
import com.jomeerkatz.springboot_backend_cc.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImpJUnitTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl bookDaoImplUnderTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBook();

        bookDaoImplUnderTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("isbn-123"),
                eq("book-title-test"),
                eq(1L)
        );
    }

    @Test
    public void testThatCreatesCorrecSqlReadOneBook(){
        bookDaoImplUnderTest.getBookByIsbn("test-isbn");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("test-isbn")
        );
    }

    @Test
    public void testThatCreateCorrectSQLReadManyBooks() {
        bookDaoImplUnderTest.findAllBooks();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

}
