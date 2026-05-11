import lombok.Getter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameServer {
    public static final int PORT = 9060;
    private boolean running = true;
    private ServerSocket serverSocket;

    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Getter private List<ClientThread> activeClients = new ArrayList<>();
    @Getter private GameManager gameManager;

    public GameServer() {
        gameManager = new GameManager(this);
        gameManager.loadQuestions();

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            int idCounter = 1;
            while (running) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket, this, idCounter++);
                activeClients.add(clientThread);

                threadPool.execute(clientThread);
            }
        } catch (IOException e) {
            System.out.println("Server socket closed.");
        }
    }

    public synchronized void broadcast(String message) {
        for (ClientThread client : activeClients) {
            client.sendMessage(message);
        }
    }

    public synchronized void resetPlayerAnswers() {
        for (ClientThread client : activeClients) {
            client.resetRound();
        }
    }

    public synchronized boolean allPlayersAnswered() {
        for (ClientThread client : activeClients) {
            if (!client.isHasAnswered()) return false;
        }
        return true;
    }

    public void stopServer() {
        this.running = false;
        broadcast("Serverul se inchide...");

        // Graceful shutdown al Thread Pool-ului
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
            if (serverSocket != null) serverSocket.close();
        } catch (Exception e) {
            threadPool.shutdownNow();
        }
    }

    public void removeClient(ClientThread client) {
        activeClients.remove(client);
    }

    public static void main(String[] args) {
        new GameServer();
    }
}