package com.jomeerkatz.springboot_backend_cc.repositories;


import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.Author;
import com.jomeerkatz.springboot_backend_cc.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // starts the entire Spring ApplicationContext
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryImplIntegrationsTests {

    private final AuthorRepository authorDaoUnderTest;

    @Autowired
    public AuthorRepositoryImplIntegrationsTests(final AuthorRepository authorDao){
        this.authorDaoUnderTest = authorDao;
    }

    @Test
    public void TestThatAuthorCanBeCreatedAndRecalled(){

        Author author = TestDataUtil.createTestAuthor();

        authorDaoUnderTest.save(author);

        Optional<Author> authorList = authorDaoUnderTest.findById(author.getId());

        assertThat(authorList).isPresent();

        assertThat(authorList.get()).isEqualTo(author);

    }


    @Test
    public void TestThatMultipleAuthorCanBeCreatedAndRecalled() {
        Author firstAuthor = TestDataUtil.createTestAuthor();
        Author secondAuthor = TestDataUtil.createTestAuthorSecond();
        Author thirdAuthor = TestDataUtil.createTestAuthorThird();

        authorDaoUnderTest.save(firstAuthor);
        authorDaoUnderTest.save(secondAuthor);
        authorDaoUnderTest.save(thirdAuthor);

        Iterable<Author> authorList = authorDaoUnderTest.findAll();

        assertThat(authorList).hasSize(3);

        assertThat(authorList).containsExactly(firstAuthor, secondAuthor, thirdAuthor);
    }

    @Test
    public void TestThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        authorDaoUnderTest.save(author);
        author.setName("new updated name");
        author.setAge(21);
        authorDaoUnderTest.save(author);
        Optional<Author> result = authorDaoUnderTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result).contains(author);
    }

    @Test
    public void TestThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthor();

        Author savedAuthor = authorDaoUnderTest.save(author);

        authorDaoUnderTest.deleteById(savedAuthor.getId());

        Optional<Author> result = authorDaoUnderTest.findById(savedAuthor.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void TestThatAuthorsUnder80Recalled(){
        Author firstAuthor = TestDataUtil.createTestAuthor();
        Author secondAuthor = TestDataUtil.createTestAuthorSecond();
        Author thirdAuthor = TestDataUtil.createTestAuthorThird();

        Author result1 = authorDaoUnderTest.save(firstAuthor);
        Author result2 = authorDaoUnderTest.save(secondAuthor);
        Author result3 = authorDaoUnderTest.save(thirdAuthor);

        Iterable<Author> result = authorDaoUnderTest.ageLessThan(80);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(result2, result3);
    }

    @Test
    public void TestThatAuthorsGreater30Recalled(){
        Author firstAuthor = TestDataUtil.createTestAuthor();
        Author secondAuthor = TestDataUtil.createTestAuthorSecond();
        Author thirdAuthor = TestDataUtil.createTestAuthorThird();

        Author result1 = authorDaoUnderTest.save(firstAuthor);
        Author result2 = authorDaoUnderTest.save(secondAuthor);
        Author result3 = authorDaoUnderTest.save(thirdAuthor);

        Iterable<Author> result = authorDaoUnderTest.findAuthorWithAgeGreaterThan(30);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(result1, result3);
    }
}

