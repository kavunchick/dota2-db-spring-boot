package cz.cvut.fit.tjv.dotapedia.service;

import cz.cvut.fit.tjv.dotapedia.domain.Championship;
import cz.cvut.fit.tjv.dotapedia.domain.Player;
import cz.cvut.fit.tjv.dotapedia.domain.Team;
import cz.cvut.fit.tjv.dotapedia.repository.ChampionshipRepository;
import cz.cvut.fit.tjv.dotapedia.repository.PlayerRepository;
import cz.cvut.fit.tjv.dotapedia.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Transactional
public class TeamServiceImpl extends CrudServiceImpl<Team, Long> implements TeamService {
    private TeamRepository teamRepository;
    @Getter
    private ChampionshipRepository championshipRepository;
    @Getter
    private PlayerRepository playerRepository;

    @Override
    public void deleteById(Long aLong) throws EntityNotFoundException {
        if (getRepository().existsById(aLong)) {
            for (Championship champ : getRepository().findById(aLong).get().getParticipatedIn()) {
                champ.getParticipants().remove(aLong);
                getChampionshipRepository().save(champ);
            }
            for (Championship champ : getRepository().findById(aLong).get().getWin()) {
                champ.setWinner(null);
                getChampionshipRepository().save(champ);
            }
            for (Player player : getRepository().findById(aLong).get().getMembers()) {
                player.setTeam(null);
                getPlayerRepository().save(player);
            }
            getRepository().deleteById(aLong);
        } else
            throw new EntityNotFoundException("Entity with id: " + aLong + " does not exist.");
    }

    @Override
    public void addTeamToChampionship(Long teamId, Long championshipId) throws EntityNotFoundException, IllegalArgumentException {
        if (!getRepository().existsById(teamId) || !getChampionshipRepository().existsById(championshipId))
            throw new EntityNotFoundException("Provided championship or team does not exist.");
        Team team = getRepository().findById(teamId).get();
        Championship championship = getChampionshipRepository().findById(championshipId).get();
        if (championship.getParticipants().contains(team))
            throw new IllegalArgumentException("Provided team is already invited to this championship");
        championship.getParticipants().add(team);
        team.getParticipatedIn().add(championship);

        getRepository().save(team);
        getChampionshipRepository().save(championship);
    }

    @Override
    public Iterable<Team> readAll() {
        return teamRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void removeTeamFromChampionship(Long teamId, Long championshipId) throws IllegalArgumentException, EntityNotFoundException {
        if (!getRepository().existsById(teamId) && !getChampionshipRepository().existsById(championshipId))
            throw new EntityNotFoundException("Provided championship or team does not exist.");
        Team team = getRepository().findById(teamId).get();
        Championship championship = getChampionshipRepository().findById(championshipId).get();
        if (!championship.getParticipants().contains(team))
            throw new IllegalArgumentException("The team was not invited to this championship.");
        championship.getParticipants().remove(team);
        team.getParticipatedIn().remove(championship);

        getRepository().save(team);
        getChampionshipRepository().save(championship);
    }

    @Override
    public Iterable<Team> businessOperation() {
        return teamRepository.allParticipatingTeamsInChampionships();
    }

    @Override
    protected CrudRepository<Team, Long> getRepository() {
        return teamRepository;
    }
}
