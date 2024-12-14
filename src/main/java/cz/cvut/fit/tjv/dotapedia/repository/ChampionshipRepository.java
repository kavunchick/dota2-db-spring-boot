package cz.cvut.fit.tjv.dotapedia.repository;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChampionshipRepository extends CrudRepository<Championship, Long>{
    @Query(value = "select p from Championship p order by p.id ASC")
    Iterable<Championship> findAllByOrderByIdAsc();

    @Query(value = "SELECT c FROM Championship c WHERE c.endDate = (SELECT MAX(c2.endDate) FROM Championship c2)")
    Championship findChampionshipByClosestEndDate();
}
