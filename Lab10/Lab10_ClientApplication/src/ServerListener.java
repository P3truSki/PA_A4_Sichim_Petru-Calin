import lombok.RequiredArgsConstructor;
import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
public class ServerListener extends Thread {
    private final BufferedReader in;

    @Override
    public void run() {
        try {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            System.out.println("\n Conexiunea este inchisa");
            System.exit(0);
        }
    }
}