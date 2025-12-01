package com.jomeerkatz.springboot_backend_cc.service.impl;

import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;
import com.jomeerkatz.springboot_backend_cc.repository.BookRepository;
import com.jomeerkatz.springboot_backend_cc.service.BookService;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(BookEntity bookEntity, String isbn) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<BookEntity> getById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isbnExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        // here again, we already checked before in controller layer, if isbn is even existing
        // get the actual record out of the db
        // check what is not null in bookEntity
        // then put it in
        bookEntity.setIsbn(isbn);
        return bookRepository.findById(isbn).map(existingBookEntity -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(
                    existingBookEntity::setTitle
            );

            // there is isbn -> which should not be updated
            // there is author_id -> which also should not be updated??
            return bookRepository.save(existingBookEntity);
        }).orElseThrow(() -> new RuntimeException("book isbn doesn't exists!"));


    }
}
