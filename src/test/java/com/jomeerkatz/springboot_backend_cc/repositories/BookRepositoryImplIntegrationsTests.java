package com.jomeerkatz.springboot_backend_cc.repositories;



import com.jomeerkatz.springboot_backend_cc.domain.Author;
import com.jomeerkatz.springboot_backend_cc.domain.Book;
import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.repository.AuthorRepository;
import com.jomeerkatz.springboot_backend_cc.repository.BookRepository;
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
public class BookRepositoryImplIntegrationsTests {

    private final BookRepository bookDaoImplUnderTest;

    private final AuthorRepository authorDaoImpl;

    @Autowired
    public BookRepositoryImplIntegrationsTests(final BookRepository bookDaoImpl, final AuthorRepository authorDaoImpl){
        this.bookDaoImplUnderTest = bookDaoImpl;
        this.authorDaoImpl = authorDaoImpl;
    }

    @Test
    public void TestThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();

        Book book = TestDataUtil.createTestBook(author);

        Book resultBook = bookDaoImplUnderTest.save(book);

        Optional<Book> bookList = bookDaoImplUnderTest.findById(book.getIsbn());

        assertThat(bookList).isPresent();

        assertThat(bookList.get()).isEqualTo(resultBook);
    }

    @Test
    public void TestThatMultipleBooksCreatedAndRecalled() {
        // create multiple Books
        Book book = TestDataUtil.createTestBook(TestDataUtil.createTestAuthor());
        Book bookSecond = TestDataUtil.createTestBookSecond(TestDataUtil.createTestAuthorSecond());
        Book bookThird = TestDataUtil.createTestBookThird(TestDataUtil.createTestAuthorThird());

        // create one Author for foreign containt
        Author author = TestDataUtil.createTestAuthor();

        // connect book with author
        book.setAuthor(author);
        bookSecond.setAuthor(author);
        bookThird.setAuthor(author);

        // save author
        authorDaoImpl.save(author);

        // save books
        bookDaoImplUnderTest.save(book);
        bookDaoImplUnderTest.save(bookSecond);
        bookDaoImplUnderTest.save(bookThird);

        // get all books again
        Iterable<Book> bookList = bookDaoImplUnderTest.findAll();

        // assertThat by compare the size of the return
        assertThat(bookList).hasSize(3);

        assertThat(bookList).containsExactly(book, bookSecond, bookThird);

    }

    @Test
    public void TestThatUpdatesExistingBook() {
        Author author = TestDataUtil.createTestAuthor();
        Book book = TestDataUtil.createTestBook(author);

        authorDaoImpl.save(author);
        book.setAuthor(author);
        bookDaoImplUnderTest.save(book);
        Author authorSecond = TestDataUtil.createTestAuthorSecond();
        authorDaoImpl.save(authorSecond);

        book.setAuthor(authorSecond); // update author ID
        book.setTitle("updated-title");

        Book savedBook = bookDaoImplUnderTest.save(book);

        Optional<Book> result = bookDaoImplUnderTest.findById(book.getIsbn());

        assertThat(result.isPresent());

        assertThat(result).contains(savedBook);

    }

    @Test
    public void TestThatDeletesExistingBook(){

        Book book = TestDataUtil.createTestBook(TestDataUtil.createTestAuthor());
        Book savedBook = bookDaoImplUnderTest.save(book);

        bookDaoImplUnderTest.deleteById(savedBook.getIsbn());

        Optional<Book> result = bookDaoImplUnderTest.findById(book.getIsbn());

        assertThat(result).isEmpty();
    }
}