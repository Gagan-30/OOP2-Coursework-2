import java.util.Objects;

public class Card implements Comparable<Card> {
    private long id;
    private String name;
    private Rank rank;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return this.id == other.id &&
               this.name.equals(other.name) &&
               this.rank == other.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rank);
    }

    @Override
    public int compareTo(Card other) {
        // First compare by rank (UNIQUE first)
        int rankCompare = this.rank.ordinal() - other.rank.ordinal();
        if (rankCompare != 0) return rankCompare;

        // Then compare by name
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) return nameCompare;

        // Finally compare by id
        return Long.compare(this.id, other.id);
    }
}
