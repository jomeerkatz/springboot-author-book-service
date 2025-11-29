package com.jomeerkatz.springboot_backend_cc.service.impl;

import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.repository.AuthorRepository;
import com.jomeerkatz.springboot_backend_cc.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(final AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity newAuthorEntity) {
        return authorRepository.save(newAuthorEntity);
    }
}
