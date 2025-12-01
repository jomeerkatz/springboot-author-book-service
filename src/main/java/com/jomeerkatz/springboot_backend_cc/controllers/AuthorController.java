package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.domain.dto.AuthorDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.mappers.Mapper;
import com.jomeerkatz.springboot_backend_cc.service.AuthorService;
import com.jomeerkatz.springboot_backend_cc.service.impl.AuthorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(final AuthorServiceImpl authorService, final Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path="/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity createResult = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(createResult), HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorsList = authorService.getAllAuthors();
        return authorsList
                .stream()
                .map(authorMapper::mapTo)
                .toList();
    }

    @GetMapping( path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        Optional<AuthorEntity> result = authorService.getAuthorById(id);
        return result.map(resultAuthorEntity -> new ResponseEntity<>(authorMapper.mapTo(resultAuthorEntity), HttpStatus.OK)).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping( path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdate(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ){
        if (authorService.idExists(id)) {
            authorDto.setId(id);
            AuthorEntity resultAuthorEntity = authorService.save(authorMapper.mapFrom(authorDto));
            return new ResponseEntity<>(authorMapper.mapTo(resultAuthorEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping( path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id,
                                                   @RequestBody AuthorDto authorDto){
        if (authorService.idExists(id)) {
            authorDto.setId(id);
            AuthorEntity resultAuthorEntity = authorService.partialUpdate(id, authorMapper.mapFrom(authorDto));
            return new ResponseEntity<>(authorMapper.mapTo(resultAuthorEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
