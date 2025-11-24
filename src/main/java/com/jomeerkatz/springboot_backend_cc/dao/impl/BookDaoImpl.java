package com.jomeerkatz.springboot_backend_cc.dao.impl;

import com.jomeerkatz.springboot_backend_cc.dao.BookDao;
import com.jomeerkatz.springboot_backend_cc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl (final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update(
                "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor_id()
        );
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        List<Book> bookList = jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(),
                isbn
        );

        return bookList.stream().findFirst();
    }

    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .author_id(rs.getLong("author_id"))
                    .build();
        }
    }
}
