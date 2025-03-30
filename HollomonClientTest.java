import java.io.IOException;
import java.util.List;

public class HollomonClientTest {
    public static void main(String[] args) {
        try {
            // Test connection and basic functionality
            testConnection();
            
            // Test trading functionality
            testTradingOperations();
            
            System.out.println("All tests passed successfully!");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testConnection() throws IOException {
        String server = "netsrv.cim.rhul.ac.uk";
        int port = 1812;
        String username = "toward";
        String password = "artistpopulationpretty"; 

        HollomonClient client = new HollomonClient(server, port);
        try {
            List<Card> result = client.login(username, password);
            if (result == null) {
                throw new AssertionError("Login failed");
            }
            System.out.println("Connection test passed");
        } finally {
            client.close();
        }
    }

    private static void testTradingOperations() throws IOException {
        String server = "netsrv.cim.rhul.ac.uk";
        int port = 1812;
        String username = "toward"; 
        String password = "artistpopulationpretty"; 

        HollomonClient client = new HollomonClient(server, port);
        try {
            // Login first
            List<Card> cards = client.login(username, password);
            if (cards == null) {
                throw new AssertionError("Login failed for trading tests");
            }

            // Test getCredits()
            long credits = client.getCredits();
            System.out.println("Credits: " + credits);
            assert credits >= 0 : "Should get valid credits";

            // Test getCards()
            List<Card> currentCards = client.getCards();
            assert currentCards != null : "Should get card list";
            System.out.println("Card count: " + currentCards.size());

            // Test getOffers()
            List<Card> offers = client.getOffers();
            assert offers != null : "Should get offers list";
            System.out.println("Offer count: " + offers.size());

            if (!offers.isEmpty()) {
                // Test buyCard() with first available offer
                boolean bought = client.buyCard(offers.get(0));
                System.out.println("Buy result: " + bought);
            }

            if (!currentCards.isEmpty()) {
                // Test sellCard() with first owned card
                boolean sold = client.sellCard(currentCards.get(0), 10);
                System.out.println("Sell result: " + sold);
            }

            System.out.println("Trading operations test passed");
        } finally {
            client.close();
        }
    }
}