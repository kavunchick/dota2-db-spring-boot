package cz.cvut.fit.tjv.dotapedia.repository;

import cz.cvut.fit.tjv.dotapedia.domain.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    @Query(value = "select p from Team p order by p.id ASC")
    Iterable<Team> findAllByOrderByIdAsc();

    @Query(value = "select t from Team t where t.worldRanking < :rank")
    Collection<Team> findByWorldRankingGreaterThan(@Param("rank") int rank);

    @Query(value = "select *\n" +
            "from team t\n" +
            "where t.id in (\n" +
            "    select t.participants_id\n" +
            "    from team_participated_in t\n" +
            "    group by t.participants_id\n" +
            "    having count(*) = (select count(*) from championship)\n" +
            "    )", nativeQuery = true)
    Collection<Team> allParticipatingTeamsInChampionships();

}
