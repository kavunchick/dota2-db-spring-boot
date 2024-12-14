package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Team;
import jakarta.persistence.EntityNotFoundException;

public interface TeamService extends CrudService<Team, Long> {
    @Override
    void deleteById(Long aLong) throws EntityNotFoundException;

    void addTeamToChampionship(Long teamId, Long championshipId) throws EntityNotFoundException, IllegalArgumentException;

    Iterable<Team> businessOperation();

    void removeTeamFromChampionship(Long teamId, Long championshipId) throws IllegalArgumentException, EntityNotFoundException;
}
