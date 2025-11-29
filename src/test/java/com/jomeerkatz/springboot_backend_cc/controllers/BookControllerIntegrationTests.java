package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.dto.BookDto;
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

    private final MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatesBookReturnsHttpStatus201Created() throws Exception{
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn()) // we do this, bec we want to
                        // act like it is a real request. and a real request will have as a pathvariable an isbn
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                // what should we get back?
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatesBookReturnsCreatedBook() throws Exception{
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String jsonBookDto = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn()) // we do this, bec we want to
                        // act like it is a real request. and a real request will have as a pathvariable an isbn
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBookDto)
        ).andExpect(
                // what should be inside the body?
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }
}
