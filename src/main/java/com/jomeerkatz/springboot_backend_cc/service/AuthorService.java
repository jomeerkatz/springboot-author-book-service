package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity newAuthorEntity);
}
