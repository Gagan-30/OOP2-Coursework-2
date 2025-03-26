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

    public long getCredits() throws IOException {
        try {
            writer.write("CREDITS\n");
            writer.flush();
            
            String response = cardInput.readResponse();
            if (response == null || response.equals("ERROR")) {
                return -1;
            }
            
            // Response should be the credit amount followed by OK
            long credits = Long.parseLong(response);
            cardInput.readResponse(); // Read the OK line
            return credits;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public List<Card> getCards() throws IOException {
        writer.write("CARDS\n");
        writer.flush();
        
        List<Card> cards = new ArrayList<>();
        Card card;
        while ((card = cardInput.readCard()) != null) {
            cards.add(card);
        }
        Collections.sort(cards);
        return cards;
    }

    public List<Card> getOffers() throws IOException {
        writer.write("OFFERS\n");
        writer.flush();
        
        List<Card> offers = new ArrayList<>();
        Card card;
        while ((card = cardInput.readCard()) != null) {
            offers.add(card);
        }
        Collections.sort(offers);
        return offers;
    }

    public boolean buyCard(Card card) throws IOException {
        if (card == null) {
            return false;
        }
        
        writer.write("BUY " + card.getId() + "\n");
        writer.flush();
        
        String response = cardInput.readResponse();
        return response != null && response.equals("OK");
    }

    public boolean sellCard(Card card, long price) throws IOException {
        if (card == null || price < 0) {
            return false;
        }
        
        writer.write("SELL " + card.getId() + " " + price + "\n");
        writer.flush();
        
        String response = cardInput.readResponse();
        return response != null && response.equals("OK");
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