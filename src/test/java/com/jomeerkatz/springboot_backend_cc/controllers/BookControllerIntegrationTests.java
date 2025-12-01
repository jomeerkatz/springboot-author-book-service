package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.dto.BookDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;
import com.jomeerkatz.springboot_backend_cc.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class BookControllerIntegrationTests {

    private BookServiceImpl bookService;

    private final MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(final MockMvc mockMvc, BookServiceImpl bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreatesBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn()) // we do this, bec we want to
                // act like it is a real request. and a real request will have as a pathvariable an isbn
                .contentType(MediaType.APPLICATION_JSON).content(jsonBookDto)).andExpect(
                // what should we get back?
                MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreatesBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn()) // we do this, bec we want to
                // act like it is a real request. and a real request will have as a pathvariable an isbn
                .contentType(MediaType.APPLICATION_JSON).content(jsonBookDto)).andExpect(
                // what should be inside the body?
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())).andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()));
    }

    @Test
    public void testThatGetAllBooksReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON)).andExpect(
                // what should we get back?
                MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatChecksReturnBodyFromGetAllBooks() throws Exception {
        BookEntity bookEntityFirst = TestDataUtil.createTestBook(null);
        BookEntity bookEntitySecond = TestDataUtil.createTestBookSecond(null);

        bookService.save(bookEntityFirst, bookEntityFirst.getIsbn());
        bookService.save(bookEntitySecond, bookEntitySecond.getIsbn());

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].isbn")
                        .value(bookEntityFirst.getIsbn()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].title")
                        .value(bookEntityFirst.getTitle()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[1].isbn")
                        .value(bookEntitySecond.getIsbn()))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[1].title")
                        .value(bookEntitySecond.getTitle()));
    }

    @Test
    public void testThatGetBookByIsbnReturnsHttpStatus200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        BookEntity resultBookEntity = bookService.save(bookEntity, bookEntity.getIsbn());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + resultBookEntity.getIsbn())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatTriesToGetBookByIsbnReturnsHttpStatus404NotFound () throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatChecksReturnBodyFromGetBookByIsbn() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);

        bookService.save(bookEntity, bookEntity.getIsbn());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn")
                        .value(bookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value(bookEntity.getTitle()));
    }

    @Test
    public void testThatFullUpdatesBookSuccessfullyReturnsHttp200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        BookEntity resultBookEntity = bookService.save(bookEntity, bookEntity.getIsbn());

        BookDto updatedNewBook = TestDataUtil.createTestBookDtoA(null);
        updatedNewBook.setIsbn(resultBookEntity.getIsbn());

        String jsonUpdatedBook = objectMapper.writeValueAsString(updatedNewBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + updatedNewBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdatedBook))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatChecksReturnBodyFromFullUpdateBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        BookEntity resultBookEntity = bookService.save(bookEntity, bookEntity.getIsbn());

        BookDto updatedBookDto = TestDataUtil.createTestBookDtoA(null);
        updatedBookDto.setIsbn(resultBookEntity.getIsbn());

        String jsonUpdatedBook = objectMapper.writeValueAsString(updatedBookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + resultBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdatedBook))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(updatedBookDto.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedBookDto.getTitle()));
    }

    @Test
    public void testThatPartialUpdatesBookSuccessfullyReturns200_OK() throws Exception{
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        BookEntity resultBookEntity = bookService.save(bookEntity, bookEntity.getIsbn());

        BookDto updatedNewBook = TestDataUtil.createTestBookDtoA(null);
        updatedNewBook.setIsbn(resultBookEntity.getIsbn());

        updatedNewBook.setTitle("NEW PARTIAL UPDATED TITLE");
        updatedNewBook.setIsbn(null);

        String jsonUpdatedBook = objectMapper.writeValueAsString(updatedNewBook);

        mockMvc.perform(MockMvcRequestBuilders.patch("/books/" + resultBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdatedBook))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatPartialUpdatesBookSuccessfullyReturnsCorrecBody() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        BookEntity resultBookEntity = bookService.save(bookEntity, bookEntity.getIsbn());

        BookDto updatedBookDto = TestDataUtil.createTestBookDtoA(null);
        updatedBookDto.setIsbn(resultBookEntity.getIsbn());
        updatedBookDto.setTitle("NEW PARTIAL UPDATED TITLE");
        updatedBookDto.setIsbn(null);

        String jsonUpdatedBook = objectMapper.writeValueAsString(updatedBookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + resultBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdatedBook))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(resultBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedBookDto.getTitle()));
    }
}
