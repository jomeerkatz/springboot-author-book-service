package com.jomeerkatz.springboot_backend_cc.dao.impl.IntegrationTests;


import com.jomeerkatz.springboot_backend_cc.dao.impl.AuthorDaoImpl;
import com.jomeerkatz.springboot_backend_cc.dao.impl.BookDaoImpl;
import com.jomeerkatz.springboot_backend_cc.domain.Author;
import com.jomeerkatz.springboot_backend_cc.domain.Book;
import com.jomeerkatz.springboot_backend_cc.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    public void TestThatMultipleBooksCreatedAndRecalled() {
        // create multiple Books
        Book book = TestDataUtil.createTestBook();
        Book bookSecond = TestDataUtil.createTestBookSecond();
        Book bookThird = TestDataUtil.createTestBookThird();

        // create one Author for foreign containt
        Author author = TestDataUtil.createTestAuthor();

        // connect book with author
        book.setAuthor_id(author.getId());
        bookSecond.setAuthor_id(author.getId());
        bookThird.setAuthor_id(author.getId());

        // save author
        authorDaoImpl.create(author);

        // save books
        bookDaoImplUnderTest.create(book);
        bookDaoImplUnderTest.create(bookSecond);
        bookDaoImplUnderTest.create(bookThird);

        // get all books again
        List<Book> bookList = bookDaoImplUnderTest.findAllBooks();

        // assertThat by compare the size of the return
        assertThat(bookList).hasSize(3);

        assertThat(bookList).containsExactly(book, bookSecond, bookThird);

    }
}


//private String isbn;
//private String title;
//private Long author_id;