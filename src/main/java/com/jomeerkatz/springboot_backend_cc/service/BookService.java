package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;

import java.awt.print.Book;
import java.util.List;

public interface BookService {
    BookEntity createBook(BookEntity bookEntity, String isbn);
    List<BookEntity> getAllBooks();
}
