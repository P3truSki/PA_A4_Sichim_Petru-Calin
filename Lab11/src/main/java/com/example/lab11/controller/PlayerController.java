package com.example.lab11.controller;

import com.example.lab11.model.Player;
import com.example.lab11.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    // Afiseaza toti jucatorii din baza de date
    @GetMapping
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    // Adauga un jucator nou
    @PostMapping
    public Player addPlayer(@RequestBody Player player) {
        return playerRepository.save(player);
    }
}