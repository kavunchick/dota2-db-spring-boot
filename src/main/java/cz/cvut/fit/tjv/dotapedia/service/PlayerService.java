package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Player;
import jakarta.persistence.EntityNotFoundException;

public interface PlayerService  extends CrudService<Player, Long>{
    void addPlayerToTeam(Long teamId, Long playerId) throws EntityNotFoundException, IllegalArgumentException;
    void removePlayerFromTeam(Long playerId) throws IllegalArgumentException, EntityNotFoundException;
    void standIn(Long origPlayer, Long player2) throws IllegalArgumentException, EntityNotFoundException;
}
