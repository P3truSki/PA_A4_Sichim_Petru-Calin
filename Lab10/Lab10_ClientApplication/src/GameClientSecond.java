import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClientSecond {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("127.0.0.1", 9060);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectat! exit - iesi ; start - incepi jocul pentru toti");
            new ServerListener(in).start();
            while (true) {
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(command);
            }
        } catch (Exception e) {
            System.out.println("Eroare de conexiune: " + e.getMessage());
        }
    }
}