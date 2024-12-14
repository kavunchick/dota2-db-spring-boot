package cz.cvut.fit.tjv.dotapedia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
public class Team implements EntityWithId<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tjvseq")
    @SequenceGenerator(name = "tjvseq", sequenceName = "team_id_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    private String sponsor;
    @Nullable
    private Integer worldRanking;
    @OneToMany (targetEntity = Player.class ,fetch=FetchType.EAGER,mappedBy = "team")
    @JsonIgnore
    private List<Player> members= new ArrayList<>();
    @ManyToMany
    @JsonIgnore
    private List<Championship> participatedIn= new ArrayList<>();
    @OneToMany (targetEntity = Championship.class, fetch=FetchType.EAGER, mappedBy = "winner")
    @JsonIgnore
    private List<Championship> win= new ArrayList<>();
}
