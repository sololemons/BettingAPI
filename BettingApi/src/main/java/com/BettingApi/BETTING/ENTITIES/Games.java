package com.BettingApi.BETTING.ENTITIES;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
