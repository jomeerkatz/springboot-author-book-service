package com.jomeerkatz.springboot_backend_cc.dao;

import com.jomeerkatz.springboot_backend_cc.domain.Author;

import java.util.Optional;

public interface AuthorDao  {
    void create(Author author);
    Optional<Author> getAuthorById(Long id);
}
