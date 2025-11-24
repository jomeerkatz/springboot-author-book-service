package com.jomeerkatz.springboot_backend_cc.dao.impl;

import com.jomeerkatz.springboot_backend_cc.dao.AuthorDao;
import com.jomeerkatz.springboot_backend_cc.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl (final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                author.getId(),
                author.getName(),
                author.getAge()
        );
    }

    @Override
    public Optional<Author> getAuthorById(Long id) { // or findOne
        List<Author> listAuthors = jdbcTemplate.query(
                "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(),
                id
        );

        return listAuthors.stream().findFirst();
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException { // rs = result of the query
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}


//public class Author {
//    private Long id;
//    private String name;
//    private Integer age;
//}
