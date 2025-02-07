package com.BettingApi.BETTING.ENTITIES;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "odds")
@Data
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oddsId")
    private Long oddsId;

    @Column(name = "oddsValue")
    private Double oddsValue; // The value of the odds for a market

}
