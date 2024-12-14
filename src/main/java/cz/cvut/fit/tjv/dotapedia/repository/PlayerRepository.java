package cz.cvut.fit.tjv.dotapedia.repository;

import cz.cvut.fit.tjv.dotapedia.domain.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    @Query(value = "select p from Player p order by p.id ASC")
    Iterable<Player> findAllByOrderByIdAsc();
}
