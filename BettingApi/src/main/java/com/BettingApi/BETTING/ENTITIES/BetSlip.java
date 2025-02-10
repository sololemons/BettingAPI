package com.BettingApi.BETTING.ENTITIES;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BETSLIP")
@Data
public class BetSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "betSlipId")
    private Long betSlipId;

    @Embedded
    private MatchInfo matchInfo;

    @Column(name = "market")
    private String market;

    @Column(name = "pick" ,nullable = false)
    @JsonProperty("pick")
    private String pick;

    @Column(name = "odds")
    private double odds;


    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    @JsonProperty("status")
    private betStatus status = betStatus.PENDING_PAYOUTS;

    @ManyToOne
    @JoinColumn(name = "betID")  // Foreign key linking BetSlip to Bet
    private Bet bet;
}