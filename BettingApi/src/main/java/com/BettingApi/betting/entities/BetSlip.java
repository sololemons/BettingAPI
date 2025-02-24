package com.BettingApi.betting.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "betslip")
@Data
public class BetSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "betSlip_id")
    private Long betSlipId;

    @Embedded
    private MatchInfo matchInfo;

    @Column(name = "market")
    private String market;

    @Column(name = "pick", nullable = false)
    private String pick;

    @Column(name = "odds")
    private double odds;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JsonProperty("status")
    private BetStatus status = BetStatus.PENDING_PAYOUTS;

    @ManyToOne
    @JoinColumn(name = "bet_id")
    private Bet bet;
}