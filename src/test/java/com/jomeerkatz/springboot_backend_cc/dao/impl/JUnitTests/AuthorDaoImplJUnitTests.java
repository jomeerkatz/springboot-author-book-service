package com.jomeerkatz.springboot_backend_cc.dao.impl.JUnitTests;

import com.jomeerkatz.springboot_backend_cc.dao.impl.AuthorDaoImpl;
import com.jomeerkatz.springboot_backend_cc.utils.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.Author;
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
public class AuthorDaoImplJUnitTests {
    @Mock
    private JdbcTemplate jdbcTemplate; // just for AuthorDaoImpl -> injections

    @InjectMocks
    private AuthorDaoImpl authorDaoImplUnderTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthor();

        authorDaoImplUnderTest.create(author);

        // Check whether this mock object was called with the following arguments
        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("testname"), eq(80)
        );
    }

    @Test
    public void testThatCreateCorrecSQLReadOneAuthor(){
        authorDaoImplUnderTest.getAuthorById(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatCreateCorrectSQLReadManyAuthors() {
        authorDaoImplUnderTest.findAllAuthors();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }
}
