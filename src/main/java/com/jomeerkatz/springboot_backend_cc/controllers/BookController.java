package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.domain.dto.BookDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;
import com.jomeerkatz.springboot_backend_cc.mappers.Mapper;
import com.jomeerkatz.springboot_backend_cc.service.impl.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> modelMapper;

    private BookServiceImpl bookService;

    public BookController (final Mapper<BookEntity, BookDto> modelMapper,
                           final BookServiceImpl bookService) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    // create and also update new book
    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn,
                                                    @RequestBody BookDto bookDto) {
        BookEntity bookEntity = modelMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isbnExists(isbn);
        BookEntity resultBookEntity = bookService.save(bookEntity, isbn);

        if (bookExists) {
            // update
            return new ResponseEntity<>(modelMapper.mapTo(resultBookEntity), HttpStatus.OK);
        } else {
            // create new book
            return new ResponseEntity<>(modelMapper.mapTo(resultBookEntity), HttpStatus.CREATED);
        }
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

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        if (!bookService.isbnExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookDto.setIsbn(isbn);
            BookEntity bookEntity = modelMapper.mapFrom(bookDto);
            BookEntity resultBookEntity = bookService.partialUpdate(isbn, bookEntity);
            return new ResponseEntity<>(modelMapper.mapTo(resultBookEntity), HttpStatus.OK);
        }
    }
}
