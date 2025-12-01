package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
    List<AuthorEntity> getAllAuthors();
    Optional<AuthorEntity> getAuthorById(Long id);
    boolean idExists (Long id);
    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);
    void deleteByID(Long id);
}
