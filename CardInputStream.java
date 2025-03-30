import java.io.*;
import java.util.Objects;

public class CardInputStream extends InputStream {
    private BufferedReader reader;

    // Constructor that initializes  reader with provided input stream
    public CardInputStream(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    // Reads a single byte from the stream (overridden from InputStream)
    @Override
    public int read() throws IOException {
        return reader.read();
    }

    // Reads a Card object from input stream
    public Card readCard() throws IOException {
        String line = reader.readLine();

        // If end of input or "OK" is encountered, return null
        if (line == null || line.equals("OK")) {
            return null;
        }

        // Skip non-card lines (expect the "CARD" tag)
        if (!line.equals("CARD")) {
            return null;
        }

        try {
            // Parse card attributes: id, name, rank, price
            long id = Long.parseLong(reader.readLine());
            String name = reader.readLine();
            Rank rank = Rank.valueOf(reader.readLine());  // Assuming Rank is an enum
            long price = Long.parseLong(reader.readLine());

            // Create Card object and set its price
            Card card = new Card(id, name, rank);
            card.setPrice(price);
            return card;
        } catch (Exception e) {
            // Throw an exception if card data format is invalid
            throw new IOException("Invalid card data format", e);
        }
    }

    // Reads a response from input stream (typically line of text)
    public String readResponse() throws IOException {
        return reader.readLine();
    }

    // Closes reader and stream
    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
