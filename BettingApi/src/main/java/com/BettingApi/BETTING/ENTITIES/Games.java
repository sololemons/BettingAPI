package com.BettingApi.BETTING.ENTITIES;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Games")
@Data
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "matchId")
    private Long matchId;

    @Column(name = "homeTeam")
    private String homeTeam;

    @Column(name = "awayTeam")
    private String awayTeam;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "matchId")
    private List<Markets> markets; // Each match can have multiple markets

}
