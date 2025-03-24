import java.util.Objects;

public class Card implements Comparable<Card> {
    private final long id;
    private final String name;
    private final Rank rank;
    private long price;

    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = 0;
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

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                Objects.equals(name, card.name) &&
                rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rank);
    }

    @Override
    public int compareTo(Card other) {
        // First compare by rank (UNIQUE comes first)
        int rankComparison = other.rank.compareTo(this.rank);
        if (rankComparison != 0) {
            return rankComparison;
        }
        
        // Then compare by name
        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }
        
        // Finally compare by ID
        return Long.compare(this.id, other.id);
    }
}
