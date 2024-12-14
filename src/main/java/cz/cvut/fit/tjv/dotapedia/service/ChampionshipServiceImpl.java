package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Team;
import cz.cvut.fit.tjv.dotapedia.repository.ChampionshipRepository;
import cz.cvut.fit.tjv.dotapedia.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class ChampionshipServiceImpl extends CrudServiceImpl<Championship, Long> implements ChampionshipService {
    private ChampionshipRepository championshipRepository;
    @Getter
    private TeamRepository teamRepository;

    @Override
    public void addWinner(Long champId, Long teamId) throws EntityNotFoundException, IllegalArgumentException {
        if (!getRepository().existsById(champId) || !getTeamRepository().existsById(teamId))
            throw new EntityNotFoundException("Provided championship or team does not exist.");
        Championship championship = getRepository().findById(champId).get();
        Team team = getTeamRepository().findById(teamId).get();
        if (!championship.getParticipants().contains(team))
            throw new IllegalArgumentException("The team did not participate in the tournament");
        championship.setWinner(team);
        team.getWin().add(championship);

        getRepository().save(championship);
        getTeamRepository().save(team);
    }

    @Override
    protected CrudRepository<Championship, Long> getRepository() {
        return championshipRepository;
    }

    @Override
    public Championship create(Championship e) throws IllegalArgumentException {
        if(getRepository().existsById(e.getId()))
            throw new IllegalArgumentException("Championship with this id already exist.");
        Championship res = super.create(e);
        for (Team team : e.getParticipants()) {
            if (!getTeamRepository().existsById(team.getId()))
                throw new IllegalArgumentException("Team does not exist");
            team = getTeamRepository().findById(team.getId()).get();
            team.getParticipatedIn().add(e);
            if (e.getWinner() == team) {
                team.getWin().add(e);
            }
            getTeamRepository().save(team);
        }
        return res;
    }

    @Override
    public void update(Championship e) throws EntityNotFoundException {
        if (!getRepository().existsById(e.getId()))
            throw new EntityNotFoundException("Championship with id: " + e.getId() + " does not exist.");
        List<Team> oldTeams = getRepository().findById(e.getId()).get().getParticipants();
        if (getRepository().findById(e.getId()).get().getWinner() != null) {
            getTeamRepository().findById(getRepository().findById(e.getId()).get().getWinner().getId()).get().getWin().remove(getRepository().findById(e.getId()).get());
            getTeamRepository().save(getTeamRepository().findById(getRepository().findById(e.getId()).get().getWinner().getId()).get());
        }
        for (Team team : oldTeams) {
            if (!e.getParticipants().contains(team)) {
                if (!getTeamRepository().existsById(team.getId()))
                    throw new IllegalArgumentException("Team does not exist");
                team = getTeamRepository().findById(team.getId()).get();
                team.getParticipatedIn().remove(getRepository().findById(e.getId()).get());
                getTeamRepository().save(team);
            }
        }
        for (Team team : e.getParticipants()) {
            if (!getTeamRepository().existsById(team.getId()))
                throw new IllegalArgumentException("Team does not exist");
            team = getTeamRepository().findById(team.getId()).get();
            team.getParticipatedIn().add(e);
            getTeamRepository().save(team);
        }
        getRepository().save(e);
    }

    @Override
    public void deleteById(Long aLong) throws EntityNotFoundException {
        if (!getRepository().existsById(aLong))
            throw new EntityNotFoundException("Championship with id: " + aLong + " does not exist.");
        Championship championship = getRepository().findById(aLong).get();
        for (Team team : championship.getParticipants()) {
            team = getTeamRepository().findById(team.getId()).get();
            team.getParticipatedIn().remove(championship);
            getTeamRepository().save(team);
        }
        super.deleteById(aLong);
    }

    @Override
    public Iterable<Championship> readAll() {
        return championshipRepository.findAllByOrderByIdAsc();
    }
}
