import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    public static final int PORT = 9060;
    private boolean running = true;
    private ServerSocket serverSocket;

    private List<ClientThread> activeClients = new ArrayList<>();

    public GameServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();

                System.out.println("Client connected!");
                ClientThread clientThread = new ClientThread(socket, this);
                activeClients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server socket closed.");
        }
    }

    public void stopServer() {
        this.running = false;

        System.out.println("Disconnecting all active clients...");
        for (ClientThread client : activeClients) {
            client.forceDisconnect();
        }
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error while closing server: " + e.getMessage());
        }
    }

    public void removeClient(ClientThread client) {
        activeClients.remove(client);
    }

    public static void main(String[] args) {
        new GameServer();
    }
}