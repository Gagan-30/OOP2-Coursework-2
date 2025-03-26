import java.io.*;
import java.util.Objects;

public class CardInputStream extends InputStream {
    private BufferedReader reader;

    public CardInputStream(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public int read() throws IOException {
        return reader.read();
    }

    public Card readCard() throws IOException {
        String line = reader.readLine();
        if (line == null || line.equals("OK")) {
            return null;
        }

        if (!line.equals("CARD")) {
            // Skip non-card lines (though protocol expects CARD tag)
            return null;
        }

        try {
            long id = Long.parseLong(reader.readLine());
            String name = reader.readLine();
            Rank rank = Rank.valueOf(reader.readLine());
            long price = Long.parseLong(reader.readLine());

            Card card = new Card(id, name, rank);
            card.setPrice(price);
            return card;
        } catch (Exception e) {
            throw new IOException("Invalid card data format", e);
        }
    }

    public String readResponse() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
