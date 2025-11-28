package com.jomeerkatz.springboot_backend_cc.repository;

import com.jomeerkatz.springboot_backend_cc.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
