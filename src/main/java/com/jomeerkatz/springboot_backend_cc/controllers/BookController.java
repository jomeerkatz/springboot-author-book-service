package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.domain.dto.BookDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;
import com.jomeerkatz.springboot_backend_cc.mappers.Mapper;
import com.jomeerkatz.springboot_backend_cc.service.BookService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> modelMapper;

    private BookService bookService;

    public BookController (final Mapper<BookEntity, BookDto> modelMapper,
                           final BookService bookService) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn,
                                              @RequestBody BookDto bookDto) {
        BookEntity bookEntity = modelMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(bookEntity, isbn);
        return new ResponseEntity<>(modelMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        List<BookEntity> listResultBooks = bookService.getAllBooks();
        return listResultBooks
                .stream()
                .map(modelMapper::mapTo)
                .toList();
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBookById(@PathVariable String isbn){
        Optional<BookEntity> result = bookService.getById(isbn);
        return result.map(resultBookEntity -> new ResponseEntity<>(modelMapper.mapTo(resultBookEntity), HttpStatus.OK)).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }
}
