import java.util.Objects;

public class Card implements Comparable<Card> {
    private long id;        // Card ID
    private String name;    // Card name
    private Rank rank;      // Card Rank (assumed to be an enum)
    private long price;     // Card Price (default is 0)

    // Constructor to initialize card with id, name, and rank
    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = 0; // Default price
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    // Converts the card's attributes into a readable string format
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", price=" + price +
                '}';
    }

    // Checks if two cards are equal based on id, name, and rank
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same object reference
        if (!(obj instanceof Card)) return false; // Not a Card instance
        Card other = (Card) obj;
        return this.id == other.id &&
                this.name.equals(other.name) &&
                this.rank == other.rank;
    }

    // Generates a hash code based on id, name, and rank
    @Override
    public int hashCode() {
        return Objects.hash(id, name, rank);
    }

    // Defines natural ordering for Card objects
    @Override
    public int compareTo(Card other) {
        // First compare by rank (ordinal order, assuming UNIQUE first)
        int rankCompare = this.rank.ordinal() - other.rank.ordinal();
        if (rankCompare != 0) return rankCompare;

        // Then compare by name (alphabetical order)
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) return nameCompare;

        // Finally compare by id (numeric order)
        return Long.compare(this.id, other.id);
    }
}