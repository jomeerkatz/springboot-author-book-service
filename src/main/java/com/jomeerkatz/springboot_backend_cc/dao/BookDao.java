package com.jomeerkatz.springboot_backend_cc.dao;

import com.jomeerkatz.springboot_backend_cc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);
    Optional<Book> getBookByIsbn(String isbn);
    List<Book> findAllBooks();
}
