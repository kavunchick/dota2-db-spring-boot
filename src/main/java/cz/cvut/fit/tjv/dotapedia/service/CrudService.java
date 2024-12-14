package cz.cvut.fit.tjv.dotapedia.service;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public interface CrudService<T, ID> {
    T create(T e);
    Optional<T> findById(ID id) throws EntityNotFoundException;
    Iterable<T> readAll();
    void update(T e) throws EntityNotFoundException;
    void deleteById(ID id) throws EntityNotFoundException;
}
