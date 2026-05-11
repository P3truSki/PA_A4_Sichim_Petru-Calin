package com.example.lab11.controller;
import com.example.lab11.service.GameManager;
import com.example.lab11.service.*;
import com.example.lab11.model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameManager gameManager;

    // Cand accesezi http://localhost:8080/game/start, se incarca intrebarile
    @PostMapping("/start")
    public String startGame() {
        gameManager.loadQuestions(); // Metoda adusa de tine din Lab 10
        return "Jocul a fost initiat! Intrebarile s-au incarcat in memorie.";
    }

    // Cand accesezi http://localhost:8080/game/questions, iti trimite intrebarile pe ecran
    @GetMapping("/questions")
    public List<Questions> getAllQuestions() {
        return gameManager.getQuestions();
    }
}