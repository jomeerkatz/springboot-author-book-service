package com.jomeerkatz.springboot_backend_cc.service;

import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(BookEntity bookEntity, String isbn);
}
