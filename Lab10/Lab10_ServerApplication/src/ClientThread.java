import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.io.*;
import java.net.Socket;

@RequiredArgsConstructor
public class ClientThread extends Thread {
    private final Socket socket;
    private final GameServer server;
    @Getter private final int playerId;

    private PrintWriter out;

    @Getter private int score = 0;
    @Getter private long totalTime = 0;
    @Getter private boolean hasAnswered = false;
    @Getter private String currentAnswer = "";
    @Getter private long answerTime = 0;

    public void sendMessage(String msg) {
        if (out != null) out.println(msg);
    }

    public void addScore(int points) { this.score += points; }
    public void addTotalTime(long time) { this.totalTime += time; }

    public void resetRound() {
        this.hasAnswered = false;
        this.currentAnswer = "";
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Mesajul inițial care îți confirmă că serverul te-a recunoscut
            out.println("Bine ai venit, Jucatorul " + playerId + "!");

            String request;
            while ((request = in.readLine()) != null) {
                request = request.trim(); // Tăiem spațiile adăugate din greșeală (ex: "start ")

                if (request.equalsIgnoreCase("start")) {
                    out.println("Ai spus: start -> Se pregateste jocul...");
                    server.getGameManager().startGame();
                }
                else if (request.length() == 1) { // Dacă a tastat o singură literă (A, B, C)
                    if (!hasAnswered) {
                        currentAnswer = request.toUpperCase();
                        answerTime = System.currentTimeMillis();
                        hasAnswered = true;
                        out.println("Ai spus: " + currentAnswer + " -> Raspuns inregistrat! Asteapta ceilalti jucatori...");
                    } else {
                        out.println("Ai raspuns deja la aceasta intrebare!");
                    }
                }
                else if (request.equalsIgnoreCase("stop server") || request.equalsIgnoreCase("close")) {
                    out.println("Ai spus: " + request + " -> Serverul se va inchide pentru toti jucatorii.");
                    server.stopServer();
                    break;
                }
                else {
                    // ECOUL: Dacă nu recunoaște comanda, pur și simplu ți-o trimite înapoi
                    out.println("Serverul a primit mesajul tau: " + request);
                }
            }
        } catch (IOException e) {
            System.out.println("Clientul " + playerId + " s-a deconectat.");
        } finally {
            try {
                socket.close();
                server.removeClient(this);
            } catch (IOException e) {}
        }
    }
}