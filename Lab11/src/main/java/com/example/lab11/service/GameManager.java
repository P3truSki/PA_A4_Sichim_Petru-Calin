
package com.example.lab11.service;
import lombok.Getter;
import com.example.lab11.model.Questions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.lab11.*;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor
@Service
@Getter
public class GameManager {
    private List<Questions> questions;
    private boolean isPlaying = false;
    private final int TIME_LIMIT_MS = 30000;

    public void loadQuestions() {
        try (FileReader reader = new FileReader("src/main/resources/questions.json")) {
            Type listType = new TypeToken<List<Questions>>(){}.getType();
            questions = new Gson().fromJson(reader, listType);
            System.out.println("Intrebari incarcate cu succes!");
        } catch (Exception e) {
            System.err.println("Eroare la citirea JSON: " + e.getMessage());
        }
    }
}