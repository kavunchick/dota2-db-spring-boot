package cz.cvut.fit.tjv.dotapedia.service;


import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import jakarta.persistence.EntityNotFoundException;

public interface ChampionshipService extends CrudService<Championship, Long>{
    public void addWinner(Long champId, Long teamId) throws EntityNotFoundException, IllegalArgumentException;
}
