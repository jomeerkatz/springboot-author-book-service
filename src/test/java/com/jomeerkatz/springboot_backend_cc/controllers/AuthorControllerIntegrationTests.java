package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
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

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    public AuthorControllerIntegrationTests (final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturns201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSaved() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntity.getAge())
        );
    }
}
