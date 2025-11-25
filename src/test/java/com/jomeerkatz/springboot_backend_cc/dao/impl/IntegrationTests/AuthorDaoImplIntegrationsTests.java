package com.jomeerkatz.springboot_backend_cc.dao.impl.IntegrationTests;


import com.jomeerkatz.springboot_backend_cc.dao.impl.AuthorDaoImpl;
import com.jomeerkatz.springboot_backend_cc.utils.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // starts the entire Spring ApplicationContext
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationsTests {

    private final AuthorDaoImpl authorDaoUnderTest;

    @Autowired
    public AuthorDaoImplIntegrationsTests(final AuthorDaoImpl authorDao){
        this.authorDaoUnderTest = authorDao;
    }

    @Test
    public void TestThatAuthorCanBeCreatedAndRecalled(){

        Author author = TestDataUtil.createTestAuthor();

        authorDaoUnderTest.create(author);

        Optional<Author> authorList =  authorDaoUnderTest.getAuthorById(1L);

        assertThat(authorList).isPresent();

        assertThat(authorList.get()).isEqualTo(author);

    }


    @Test
    public void TestThatMultipleAuthorCanBeCreatedAndRecalled() {
        Author firstAuthor = TestDataUtil.createTestAuthor();
        Author secondAuthor = TestDataUtil.createTestAuthorSecond();
        Author thirdAuthor = TestDataUtil.createTestAuthorThird();

        authorDaoUnderTest.create(firstAuthor);
        authorDaoUnderTest.create(secondAuthor);
        authorDaoUnderTest.create(thirdAuthor);

        List<Author> authorList = authorDaoUnderTest.findAllAuthors();

        assertThat(authorList).hasSize(3);

        assertThat(authorList).containsExactly(firstAuthor, secondAuthor, thirdAuthor);
    }


}

//private Long id;
//private String name;
//private Integer age;
