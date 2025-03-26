import java.io.*;
import java.util.List;

public class CardInputStreamTest {
    public static void main(String[] args) {
        try {
            // Test card reading
            testCardReading();
            
            // Test response reading
            testResponseReading();
            
            // Test error handling
            testErrorHandling();
            
            System.out.println("All CardInputStream tests passed!");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testCardReading() throws IOException {
        String cardData = "CARD\n12345\nButler\nCOMMON\n0\n";
        InputStream input = new ByteArrayInputStream(cardData.getBytes());
        CardInputStream cis = new CardInputStream(input);

        Card card = cis.readCard();
        assert card != null : "Should read a valid card";
        assert card.getId() == 12345 : "ID should match";
        assert card.getName().equals("Butler") : "Name should match";
        assert card.getRank() == Rank.COMMON : "Rank should match";
        assert card.getPrice() == 0 : "Price should match";

        System.out.println("Card reading test passed");
    }

    private static void testResponseReading() throws IOException {
        String responseData = "OK\n";
        InputStream input = new ByteArrayInputStream(responseData.getBytes());
        CardInputStream cis = new CardInputStream(input);

        String response = cis.readResponse();
        assert response.equals("OK") : "Should read response correctly";

        System.out.println("Response reading test passed");
    }

    private static void testErrorHandling() {
        try {
            String invalidData = "CARD\ninvalid\nButler\nCOMMON\n0\n";
            InputStream input = new ByteArrayInputStream(invalidData.getBytes());
            CardInputStream cis = new CardInputStream(input);
            cis.readCard();
            assert false : "Should throw IOException for invalid data";
        } catch (IOException e) {
            // Expected
            System.out.println("Error handling test passed");
        }
    }
}
