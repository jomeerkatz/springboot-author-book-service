package com.jomeerkatz.springboot_backend_cc.mappers.impl;

import com.jomeerkatz.springboot_backend_cc.domain.dto.BookDto;
import com.jomeerkatz.springboot_backend_cc.domain.entities.BookEntity;
import com.jomeerkatz.springboot_backend_cc.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import tools.jackson.databind.ObjectMapper;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {
    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) { // via constructor injection
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
