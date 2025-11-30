package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity newAuthorEntity);
    List<AuthorEntity> getAllAuthors();
    Optional<AuthorEntity> getById(Long id);
}
