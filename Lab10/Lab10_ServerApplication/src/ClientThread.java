import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void forceDisconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {
                if (request.equalsIgnoreCase("stop server")) {
                    out.println("Server stopped");
                    System.out.println("Stop server command received.");
                    server.stopServer(); // Asta va opri tot!
                    break;
                }
                else if (request.equalsIgnoreCase("stop client")) {
                    out.println("Client connection closed by server.");
                    break;
                }
                else {
                    out.println("Server received the request: " + request);
                }
            }
        } catch (IOException e) {
            System.out.println("Client forcibly disconnected or network error.");
        } finally {
            try {
                socket.close();
                server.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}