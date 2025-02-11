package com.BettingApi.BETTING.ENTITIES;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "market")
@Data
public class Markets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marketId")
    private Long marketId;

    @Column(name = "marketName")
    private String marketName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "marketID")
    private List<Odds> oddsList; // A market has multiple odds

}
