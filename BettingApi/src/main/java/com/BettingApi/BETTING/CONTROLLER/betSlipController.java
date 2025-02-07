package com.BettingApi.BETTING.CONTROLLER;


import com.BettingApi.BETTING.ENTITIES.BetSlip;
import com.BettingApi.BETTING.SERVICES.betSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/betslip")
public class betSlipController {
@Autowired
    private final betSlipService betSlipService;

    public betSlipController(com.BettingApi.BETTING.SERVICES.betSlipService betSlipService) {
        this.betSlipService = betSlipService;
    }


    @GetMapping("/user/{id}")
    public List<BetSlip> getBetSlipsByUserId(@PathVariable Long id) {
        return betSlipService.getBetSlipsByUserId(id);
    }
}
