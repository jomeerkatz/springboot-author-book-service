package com.jomeerkatz.springboot_backend_cc.dao.impl;


import com.jomeerkatz.springboot_backend_cc.domain.Author;
import com.jomeerkatz.springboot_backend_cc.domain.Book;
import com.jomeerkatz.springboot_backend_cc.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationsTests {

    private final BookDaoImpl bookDaoImplUnderTest;

    private final AuthorDaoImpl authorDaoImpl;

    @Autowired
    public BookDaoImplIntegrationsTests(final BookDaoImpl bookDaoImpl, final AuthorDaoImpl authorDaoImpl){
        this.bookDaoImplUnderTest = bookDaoImpl;
        this.authorDaoImpl = authorDaoImpl;
    }


    @Test
    public void TestThatBookCanBeCreatedAndRecalled() {
        Book book = TestDataUtil.createTestBook();

        Author author = TestDataUtil.createTestAuthor();

        authorDaoImpl.create(author);

        book.setAuthor_id(author.getId());

        bookDaoImplUnderTest.create(book);

        Optional<Book> bookList =  bookDaoImplUnderTest.getBookByIsbn(book.getIsbn());

        assertThat(bookList).isPresent();

        assertThat(bookList.get()).isEqualTo(book);
    }
}
