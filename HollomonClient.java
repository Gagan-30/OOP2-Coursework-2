import java.io.*;
import java.net.Socket;
import java.util.*;

public class HollomonClient {
    private Socket socket;
    private CardInputStream cardInput;
    private BufferedWriter writer;
    private boolean isConnected = false;

    public HollomonClient(String server, int port) {
        try {
            this.socket = new Socket(server, port);
            this.cardInput = new CardInputStream(socket.getInputStream());
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.isConnected = true;
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            this.isConnected = false;
        }
    }

    public List<Card> login(String username, String password) {
        if (!isConnected) {
            return null;
        }

        try {
            // Send credentials
            writer.write(username.toLowerCase() + "\n");
            writer.write(password + "\n");
            writer.flush();

            // Read response
            String response = cardInput.readResponse();
            if (response == null || !response.contains("logged in successfully")) {
                return null;
            }

            // Read cards
            List<Card> cards = new ArrayList<>();
            Card card;
            while ((card = cardInput.readCard()) != null) {
                cards.add(card);
            }

            // Sort cards
            Collections.sort(cards);
            return cards;
        } catch (IOException e) {
            System.err.println("Error during login: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (writer != null) writer.close();
            if (cardInput != null) cardInput.close();
            if (socket != null) socket.close();
            isConnected = false;
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
}
