package cz.cvut.fit.tjv.dotapedia.repository;

import cz.cvut.fit.tjv.dotapedia.domain.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void findByWorldRankingGreaterThan(){
        var t1 = new Team();
        var t2 = new Team();
        t1.setWorldRanking(4);
        t1.setId(1L);
        t1.setName("TestTeam1");
        t2.setWorldRanking(1);
        t2.setId(2L);
        t2.setName("TestTeam2-result");
        teamRepository.saveAll(List.of(t1, t2));
        var res = teamRepository.findByWorldRankingGreaterThan(2);
        Assertions.assertEquals(res, List.of(teamRepository.findById(t2.getId()).get()));
    }
}
