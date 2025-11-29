package com.jomeerkatz.springboot_backend_cc.repositories;



import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;
import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.repository.AuthorRepository;
import com.jomeerkatz.springboot_backend_cc.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryImplIntegrationsTests {

    private final BookRepository bookDaoImplUnderTest;

    private final AuthorRepository authorDaoImpl;

    @Autowired
    public BookEntityRepositoryImplIntegrationsTests(final BookRepository bookDaoImpl, final AuthorRepository authorDaoImpl){
        this.bookDaoImplUnderTest = bookDaoImpl;
        this.authorDaoImpl = authorDaoImpl;
    }

    @Test
    public void TestThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);

        BookEntity resultBookEntity = bookDaoImplUnderTest.save(bookEntity);

        Optional<BookEntity> bookList = bookDaoImplUnderTest.findById(bookEntity.getIsbn());

        assertThat(bookList).isPresent();

        assertThat(bookList.get()).isEqualTo(resultBookEntity);
    }

    @Test
    public void TestThatMultipleBooksCreatedAndRecalled() {
        // create multiple Books
        BookEntity bookEntity = TestDataUtil.createTestBook(TestDataUtil.createTestAuthor());
        BookEntity bookEntitySecond = TestDataUtil.createTestBookSecond(TestDataUtil.createTestAuthorSecond());
        BookEntity bookEntityThird = TestDataUtil.createTestBookThird(TestDataUtil.createTestAuthorThird());

        // create one Author for foreign containt
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        // connect book with author
        bookEntity.setAuthorEntity(authorEntity);
        bookEntitySecond.setAuthorEntity(authorEntity);
        bookEntityThird.setAuthorEntity(authorEntity);

        // save author
        authorDaoImpl.save(authorEntity);

        // save books
        bookDaoImplUnderTest.save(bookEntity);
        bookDaoImplUnderTest.save(bookEntitySecond);
        bookDaoImplUnderTest.save(bookEntityThird);

        // get all books again
        Iterable<BookEntity> bookList = bookDaoImplUnderTest.findAll();

        // assertThat by compare the size of the return
        assertThat(bookList).hasSize(3);

        assertThat(bookList).containsExactly(bookEntity, bookEntitySecond, bookEntityThird);

    }

    @Test
    public void TestThatUpdatesExistingBook() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);

        authorDaoImpl.save(authorEntity);
        bookEntity.setAuthorEntity(authorEntity);
        bookDaoImplUnderTest.save(bookEntity);
        AuthorEntity authorEntitySecond = TestDataUtil.createTestAuthorSecond();
        authorDaoImpl.save(authorEntitySecond);

        bookEntity.setAuthorEntity(authorEntitySecond); // update author ID
        bookEntity.setTitle("updated-title");

        BookEntity savedBookEntity = bookDaoImplUnderTest.save(bookEntity);

        Optional<BookEntity> result = bookDaoImplUnderTest.findById(bookEntity.getIsbn());

        assertThat(result.isPresent());

        assertThat(result).contains(savedBookEntity);

    }

    @Test
    public void TestThatDeletesExistingBook(){

        BookEntity bookEntity = TestDataUtil.createTestBook(TestDataUtil.createTestAuthor());
        BookEntity savedBookEntity = bookDaoImplUnderTest.save(bookEntity);

        bookDaoImplUnderTest.deleteById(savedBookEntity.getIsbn());

        Optional<BookEntity> result = bookDaoImplUnderTest.findById(bookEntity.getIsbn());

        assertThat(result).isEmpty();
    }
}