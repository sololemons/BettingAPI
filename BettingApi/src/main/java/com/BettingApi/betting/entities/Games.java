package com.BettingApi.betting.entities;

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
    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "away_team")
    private String awayTeam;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private List<Markets> markets;

}
