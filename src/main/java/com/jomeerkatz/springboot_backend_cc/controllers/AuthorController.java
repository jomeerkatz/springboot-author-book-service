package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.domain.dto.AuthorDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.mappers.Mapper;
import com.jomeerkatz.springboot_backend_cc.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(final AuthorService authorService, final Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path="/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity createResult = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(createResult), HttpStatus.CREATED
        );
    }
}
