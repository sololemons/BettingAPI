package com.BettingApi.BETTING.ENTITIES;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BETSLIP")
@Data
public class BetSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "betSlipId")
    private Long betSlipId;

    @Embedded
    private MatchInfo matchInfo;

    @Column(name = "market")
    private String market;

    @Column(name = "pick")
    private String pick;

    @Column(name = "odds")
    private double odds;

    @ManyToOne
    @JoinColumn(name = "betID")  // Foreign key linking BetSlip to Bet
    private Bet bet;
}