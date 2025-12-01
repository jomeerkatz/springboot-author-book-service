package com.jomeerkatz.springboot_backend_cc.service.impl;

import com.jomeerkatz.springboot_backend_cc.domain.entities.AuthorEntity;
import com.jomeerkatz.springboot_backend_cc.repository.AuthorRepository;
import com.jomeerkatz.springboot_backend_cc.service.AuthorService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(final AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity newAuthorEntity) {
        return authorRepository.save(newAuthorEntity);
    }

    @Override
    public List<AuthorEntity> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(),false).toList();
    }

    @Override
    public Optional<AuthorEntity> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean idExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        // partial update means, we want to swap out specific information
        // id existing check is already done - in case it is NOT there (unlikely), we will throw runntime exception
        // that means, we can already GET the record with the ID out of the DB and then do some updates IN THERE.
        authorEntity.setId(id);
        return authorRepository.findById(id).map(existingAuthorEntity -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthorEntity::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthorEntity::setAge);
            return authorRepository.save(existingAuthorEntity);
        }).orElseThrow( () -> new RuntimeException("Author id doesn't exists!"));
    }

    @Override
    public void deleteByID(Long id) {
        // we already checked before, if the id is existing
        authorRepository.deleteById(id);
    }
}
