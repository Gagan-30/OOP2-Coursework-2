import java.io.*;
import java.net.Socket;
import java.util.*;

public class HollomonClient {
    private Socket socket;               // Socket to communicate with the server
    private CardInputStream cardInput;   // Card input stream to read data from the server
    private BufferedWriter writer;       // Writer to send data to the server
    private boolean isConnected = false; // Flag indicating if client is connected

    // Constructor to initialize connection with server
    public HollomonClient(String server, int port) {
        try {
            // Establish a connection to server
            this.socket = new Socket(server, port);
            this.cardInput = new CardInputStream(socket.getInputStream());
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.isConnected = true; // Successfully connected
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            this.isConnected = false; // Failed to connect
        }
    }

    // Login to server with the provided credentials and returns a list of cards
    public List<Card> login(String username, String password) {
        if (!isConnected) {
            return null; // Return null if not connected
        }

        try {
            // Send login credentials
            writer.write(username.toLowerCase() + "\n");
            writer.write(password + "\n");
            writer.flush();

            // Read server's login response
            String response = cardInput.readResponse();
            if (response == null || !response.contains("logged in successfully")) {
                return null; // Return null if login fails
            }

            // Read and return the list of cards
            List<Card> cards = new ArrayList<>();
            Card card;
            while ((card = cardInput.readCard()) != null) {
                cards.add(card);
            }

            // Sort cards in natural order
            Collections.sort(cards);
            return cards;
        } catch (IOException e) {
            System.err.println("Error during login: " + e.getMessage());
            return null; // Return null if error occurs
        }
    }

    // Retrieves the current credits of the logged-in user
    public long getCredits() throws IOException {
        try {
            writer.write("CREDITS\n");
            writer.flush();

            String response = cardInput.readResponse();
            if (response == null || response.equals("ERROR")) {
                return -1; // Return -1 if there's an error
            }

            // Parse the credits amount from the response
            long credits = Long.parseLong(response);
            cardInput.readResponse(); // Read the "OK" confirmation line
            return credits;
        } catch (NumberFormatException e) {
            return -1; // Return -1 if credits format is invalid
        }
    }

    // Retrieves the list of cards
    public List<Card> getCards() throws IOException {
        writer.write("CARDS\n");
        writer.flush();

        List<Card> cards = new ArrayList<>();
        Card card;
        while ((card = cardInput.readCard()) != null) {
            cards.add(card);
        }

        // Sort cards in natural order
        Collections.sort(cards);
        return cards;
    }

    // Retrieves the list of offers available to the user
    public List<Card> getOffers() throws IOException {
        writer.write("OFFERS\n");
        writer.flush();

        List<Card> offers = new ArrayList<>();
        Card card;
        while ((card = cardInput.readCard()) != null) {
            offers.add(card);
        }

        // Sort offers in natural order
        Collections.sort(offers);
        return offers;
    }

    // Buys a card with the given id
    public boolean buyCard(Card card) throws IOException {
        if (card == null) {
            return false; // Return false if card is null
        }

        writer.write("BUY " + card.getId() + "\n");
        writer.flush();

        // Read response to see if purchase was successful
        String response = cardInput.readResponse();
        return response != null && response.equals("OK"); // Return true if purchase was successful
    }

    // Sells a card at the specified price
    public boolean sellCard(Card card, long price) throws IOException {
        if (card == null || price < 0) {
            return false; // Return false if card is null or price is invalid
        }

        writer.write("SELL " + card.getId() + " " + price + "\n");
        writer.flush();

        // Read response to see if sale was successful
        String response = cardInput.readResponse();
        return response != null && response.equals("OK"); // Return true if sale was successful
    }

    // Closes the connection and resources
    public void close() {
        try {
            if (writer != null) writer.close();  // Close writer stream
            if (cardInput != null) cardInput.close();  // Close card input stream
            if (socket != null) socket.close();  // Close socket connection
            isConnected = false; // Mark as disconnected
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
}