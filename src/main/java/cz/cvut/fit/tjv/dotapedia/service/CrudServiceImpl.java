package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.EntityWithId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class CrudServiceImpl<T extends EntityWithId<ID>, ID> implements CrudService<T, ID> {
    @Override
    public T create(T e) throws IllegalArgumentException{
        if (getRepository().existsById(e.getId()))
            throw new IllegalArgumentException(e.getClass().getSimpleName()  +" with id: " + e.getId() + " already exist.");

        return getRepository().save(e);
    }

    @Override
    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public void update(T e) throws EntityNotFoundException{
        if (getRepository().existsById(e.getId())) {
            getRepository().save(e);
            return;
        }
        throw new EntityNotFoundException(e.getClass().getSimpleName()  +" with id: " + e.getId() + " does not exist.");
    }

    @Override
    public void deleteById(ID id) throws EntityNotFoundException {
        if (getRepository().existsById(id))
            getRepository().deleteById(id);
        else
            throw new EntityNotFoundException("Entity with id: " + id + " does not exist.");
    }

    protected abstract CrudRepository<T, ID> getRepository();

}
