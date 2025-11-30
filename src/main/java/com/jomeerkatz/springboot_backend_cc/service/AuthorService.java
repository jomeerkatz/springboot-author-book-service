package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity newAuthorEntity);
    List<AuthorEntity> getAllAuthors();
    Optional<AuthorEntity> getAuthorById(Long id);
    boolean idExists (Long id);
}
