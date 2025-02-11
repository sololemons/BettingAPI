package com.BettingApi.BETTING.ENTITIES;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "odds")
@Data
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oddsId")
    private Long oddsId;

    @Column(name = "oddType")
    private String oddType;

    @Column(name = "oddsValue")
    private Double oddsValue; // The value of the odds for a market

   @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marketId")
    private Markets market;

    public Odds(String oddType) {
        this.oddType = oddType;
    }


    public Odds() {

    }
}
