import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClientSecond {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int PORT = 9060;
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to the Game Server! Type commands or 'exit' to quit.");

            while (true) {
                String command = scanner.nextLine();

                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Closing client locally...");
                    break;
                }

                out.println(command);
                String response = in.readLine();

                if (response == null) {
                    System.out.println("Server connection was lost.");
                    break;
                }
                System.out.println(response);
                if (command.equalsIgnoreCase("stop client") || command.equalsIgnoreCase("stop server")) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Network error: " + e.getMessage());
        }
    }
}