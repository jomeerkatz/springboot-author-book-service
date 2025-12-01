package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity save(BookEntity bookEntity, String isbn);
    List<BookEntity> getAllBooks();
    Optional<BookEntity> getById(String isbn);
    boolean isbnExists(String isbn);
    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
    void deleteById(String isbn);
}
