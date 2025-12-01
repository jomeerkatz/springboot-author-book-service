package com.jomeerkatz.springboot_backend_cc.controllers;

import com.jomeerkatz.springboot_backend_cc.TestDataUtil;
import com.jomeerkatz.springboot_backend_cc.domain.dto.AuthorDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
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

    private AuthorServiceImpl authorService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    public AuthorControllerIntegrationTests(final MockMvc mockMvc, AuthorServiceImpl authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
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

    @Test
    public void testThatGetAllAuthorsSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAllAuthorsSuccessfullyReturnsAuthorList() throws Exception {
        AuthorEntity authorEntityFirst = TestDataUtil.createTestAuthor();
        AuthorEntity authorEntitySecond = TestDataUtil.createTestAuthorSecond();

        authorService.save(authorEntityFirst);
        authorService.save(authorEntitySecond);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(authorEntityFirst.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(authorEntityFirst.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(authorEntitySecond.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(authorEntitySecond.getName())
        );
    }

    @Test
    public void testThatGetAuthorByIdSuccessfullyReturnsHttp200ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity resultEntity = authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + resultEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatTriesToGetAuthorByIdReturnsHttp404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorByIdSuccessfullyTestBody() throws Exception {
        AuthorEntity authorEntityFirst = TestDataUtil.createTestAuthor();
        AuthorEntity authorEntityResult = authorService.save(authorEntityFirst);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + authorEntityResult.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntityFirst.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntityFirst.getName())
        );
    }

    @Test
    public void testThatFullUpdatesAuthorSuccessfullyReturnsHttp200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity resultEntity = authorService.save(authorEntity); // author has an ID

        AuthorEntity authorEntitySecond = TestDataUtil.createTestAuthorSecond();

        authorEntitySecond.setId(resultEntity.getId());

        String jsonAuthorEntityUpdated = objectMapper.writeValueAsString(authorEntitySecond);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + resultEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorEntityUpdated)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatTriesFullUpdatesAuthorButIdNotFoundReturnsHttp404NotFound() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity resultEntity = authorService.save(authorEntity); // author has an ID

        AuthorEntity authorEntitySecond = TestDataUtil.createTestAuthorSecond();

        authorEntitySecond.setId(0L);

        String jsonAuthorEntityUpdated = objectMapper.writeValueAsString(authorEntitySecond);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + authorEntitySecond.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorEntityUpdated)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateReturnsUpdatedAuthorSuccessfullyCheckBodyOk() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity resultEntity = authorService.save(authorEntity); // author has an ID

        AuthorEntity authorEntitySecond = TestDataUtil.createTestAuthorSecond();

        authorEntitySecond.setId(resultEntity.getId());

        String jsonAuthorEntityUpdated = objectMapper.writeValueAsString(authorEntitySecond);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + resultEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorEntityUpdated)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(resultEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntitySecond.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntitySecond.getName())
        );
    }

    @Test
    public void testThatPartialUpdatesAuthorSuccessfullyReturnsHttp200_OK() throws Exception{
        AuthorEntity newAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(newAuthorEntity);

        AuthorDto partialUpdatedAuthorDto = TestDataUtil.createTestAuthorDtoFirst();
        partialUpdatedAuthorDto.setId(savedAuthor.getId());
        partialUpdatedAuthorDto.setAge(null);
        partialUpdatedAuthorDto.setName("new partial updated name");


        String jsonAuthorDto = objectMapper.writeValueAsString(partialUpdatedAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdatesAuthorSuccessfullyReturnsCorrectBody() throws Exception{
        AuthorEntity newAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(newAuthorEntity);

        AuthorDto partialUpdatedAuthorDto = TestDataUtil.createTestAuthorDtoFirst();
        partialUpdatedAuthorDto.setId(savedAuthor.getId());
        partialUpdatedAuthorDto.setAge(null);
        partialUpdatedAuthorDto.setName("new partial updated name");


        String jsonAuthorDto = objectMapper.writeValueAsString(partialUpdatedAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAuthorDto)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(partialUpdatedAuthorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedAuthor.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorSuccessfullyReturnsHttp204() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity resultEntity = authorService.save(authorEntity); // author has an ID

        Long authorIdToDelete = resultEntity.getId();

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + authorIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
