package cz.cvut.fit.tjv.dotapedia.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player implements EntityWithId<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tjvseq")
    @SequenceGenerator(name = "tjvseq", sequenceName = "player_id_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String nickname;
    @ManyToOne
    private Team team;
}
