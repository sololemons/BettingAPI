package com.BettingApi.betting.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "market")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Markets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long marketId;

    @Column(name = "market_name")
    private String marketName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "market_id")
    @JsonManagedReference
    private List<Odds> oddsList;

}
