package cz.cvut.fit.tjv.dotapedia.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
public class Championship implements EntityWithId<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tjvseq")
    @SequenceGenerator(name = "tjvseq", sequenceName = "champ_id_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int prizePool;
    @NotNull
    private Date startingDate;
    @NotNull
    private Date endDate;
    @ManyToMany(targetEntity = Team.class, mappedBy = "participatedIn")
    private List<Team> participants= new ArrayList<>();
    @ManyToOne
    private Team winner;
}
