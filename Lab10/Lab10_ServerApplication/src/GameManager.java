import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor
public class GameManager {
    private final GameServer server;
    private List<Questions> questions;
    private boolean isPlaying = false;
    private final int TIME_LIMIT_MS = 30000;

    public void loadQuestions() {
        try (FileReader reader = new FileReader("Lab10_ServerApplication/questions.json")) {
            Type listType = new TypeToken<List<Questions>>(){}.getType();
            questions = new Gson().fromJson(reader, listType);
            System.out.println("Intrebari incarcate cu succes!");
        } catch (Exception e) {
            System.err.println("Eroare la citirea JSON: " + e.getMessage());
        }
    }
    public synchronized void startGame() {
        if (isPlaying) return;
        isPlaying = true;

        new Thread(() -> {
            server.broadcast("\n=== START JOC ===");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}

            for (int i = 0; i < questions.size(); i++) {
                Questions questionsRecieved = questions.get(i);
                server.resetPlayerAnswers();

                server.broadcast("\nIntrebarea " + (i+1) + ": " + questionsRecieved.getText());
                server.broadcast(questionsRecieved.getOptions());
                server.broadcast("Aveti 30 de secunde! Trimiteti litera corecta.");

                long startTime = System.currentTimeMillis();

                while (System.currentTimeMillis() - startTime < TIME_LIMIT_MS && !server.allPlayersAnswered()) {
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                }

                evaluateRound(questionsRecieved, startTime);
            }

            announceWinners();
            isPlaying = false;
        }).start();
    }

    private void evaluateRound(Questions q, long startTime) {
        for (ClientThread player : server.getActiveClients()) {
            if (!player.isHasAnswered()) {
                player.addScore(-1);
                player.sendMessage("Timpul a expirat! Ai pierdut 1 punct.");
            } else {
                if (player.getCurrentAnswer().equalsIgnoreCase(q.getCorrectOption())) {
                    player.addScore(1);
                    long responseTime = player.getAnswerTime() - startTime;
                    player.addTotalTime(responseTime);
                    player.sendMessage("Corect! (" + (responseTime/1000) + "s)");
                } else {
                    player.sendMessage("Gresit! Raspunsul corect era " + q.getCorrectOption());
                }
            }
        }
    }

    private void announceWinners() {
        server.broadcast("\n=== JOCUL S-A TERMINAT ===");

        List<ClientThread> players = server.getActiveClients();
        players.sort((p1, p2) -> {
            if (p1.getScore() != p2.getScore()) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
            return Long.compare(p1.getTotalTime(), p2.getTotalTime());
        });

        for (int i = 0; i < players.size(); i++) {
            ClientThread p = players.get(i);
            String result = (i == 0) ? "Câștigător" : "Pierzător";
            server.broadcast(result + ": Jucatorul " + p.getPlayerId() + " | Puncte: " + p.getScore() + " | Timp total rezolvare: " + (p.getTotalTime()/1000.0) + "s");
        }
    }
}