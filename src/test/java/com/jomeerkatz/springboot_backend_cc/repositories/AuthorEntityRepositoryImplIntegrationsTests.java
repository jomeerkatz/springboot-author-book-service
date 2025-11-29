package com.jomeerkatz.springboot_backend_cc.repositories;


import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // starts the entire Spring ApplicationContext
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryImplIntegrationsTests {

    private final AuthorRepository authorDaoUnderTest;

    @Autowired
    public AuthorEntityRepositoryImplIntegrationsTests(final AuthorRepository authorDao){
        this.authorDaoUnderTest = authorDao;
    }

    @Test
    public void TestThatAuthorCanBeCreatedAndRecalled(){

        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        authorDaoUnderTest.save(authorEntity);

        Optional<AuthorEntity> authorList = authorDaoUnderTest.findById(authorEntity.getId());

        assertThat(authorList).isPresent();

        assertThat(authorList.get()).isEqualTo(authorEntity);

    }


    @Test
    public void TestThatMultipleAuthorCanBeCreatedAndRecalled() {
        AuthorEntity firstAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity secondAuthorEntity = TestDataUtil.createTestAuthorSecond();
        AuthorEntity thirdAuthorEntity = TestDataUtil.createTestAuthorThird();

        authorDaoUnderTest.save(firstAuthorEntity);
        authorDaoUnderTest.save(secondAuthorEntity);
        authorDaoUnderTest.save(thirdAuthorEntity);

        Iterable<AuthorEntity> authorList = authorDaoUnderTest.findAll();

        assertThat(authorList).hasSize(3);

        assertThat(authorList).containsExactly(firstAuthorEntity, secondAuthorEntity, thirdAuthorEntity);
    }

    @Test
    public void TestThatAuthorCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        authorDaoUnderTest.save(authorEntity);
        authorEntity.setName("new updated name");
        authorEntity.setAge(21);
        authorDaoUnderTest.save(authorEntity);
        Optional<AuthorEntity> result = authorDaoUnderTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result).contains(authorEntity);
    }

    @Test
    public void TestThatAuthorCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        AuthorEntity savedAuthorEntity = authorDaoUnderTest.save(authorEntity);

        authorDaoUnderTest.deleteById(savedAuthorEntity.getId());

        Optional<AuthorEntity> result = authorDaoUnderTest.findById(savedAuthorEntity.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void TestThatAuthorsUnder80Recalled(){
        AuthorEntity firstAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity secondAuthorEntity = TestDataUtil.createTestAuthorSecond();
        AuthorEntity thirdAuthorEntity = TestDataUtil.createTestAuthorThird();

        AuthorEntity result1 = authorDaoUnderTest.save(firstAuthorEntity);
        AuthorEntity result2 = authorDaoUnderTest.save(secondAuthorEntity);
        AuthorEntity result3 = authorDaoUnderTest.save(thirdAuthorEntity);

        Iterable<AuthorEntity> result = authorDaoUnderTest.ageLessThan(80);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(result2, result3);
    }

    @Test
    public void TestThatAuthorsGreater30Recalled(){
        AuthorEntity firstAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity secondAuthorEntity = TestDataUtil.createTestAuthorSecond();
        AuthorEntity thirdAuthorEntity = TestDataUtil.createTestAuthorThird();

        AuthorEntity result1 = authorDaoUnderTest.save(firstAuthorEntity);
        AuthorEntity result2 = authorDaoUnderTest.save(secondAuthorEntity);
        AuthorEntity result3 = authorDaoUnderTest.save(thirdAuthorEntity);

        Iterable<AuthorEntity> result = authorDaoUnderTest.findAuthorWithAgeGreaterThan(30);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(result1, result3);
    }
}

