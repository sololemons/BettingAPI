package com.BettingApi.BETTING.ENTITIES;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "bet")
@AllArgsConstructor
@NoArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Long betId;

    @Column(name = "bet_placed_on")
    private String betPlacedOn;

    @Column(name = "total_games")
    private int totalGames;

    @Column(name = "stake")
    private double stake;

    @Column(name = "total_odds")
    private double totalOdds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BetStatus status = BetStatus.PENDING_PAYOUTS;

    @Column(name = "possible_win")
    private Long possibleWin;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bet_id")
    private List<BetSlip> betSlips;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;


}