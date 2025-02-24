package com.BettingApi.betting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "odds")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odds_id")
    private Long oddsId;

    @Column(name = "odd_type")
    private String oddType;

    @Column(name = "odds_value")
    private double oddsValue; // The value of the odds for a market

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "market_id")
    private Markets market;


}
