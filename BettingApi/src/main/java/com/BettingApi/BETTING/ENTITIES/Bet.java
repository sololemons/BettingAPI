package com.BettingApi.BETTING.ENTITIES;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@Table(name = "Bet")
@AllArgsConstructor
@NoArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "betID")
    private Long betID;

    @Column(name = "betPlacedOn")
    private String betPlacedOn;

    @Column(name = "totalGames")
    private int totalGames;

    @Column(name = "stake")
    private Double stake;

    @Column(name = "totalOdds")
    private Double totalOdds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private betStatus status;

    @Column(name="possibleWin")
    private Long possibleWin;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "betID")
    private List<BetSlip> betSlips;

    @ManyToOne
    @JoinColumn(name = "userid")
    private Users users;


}