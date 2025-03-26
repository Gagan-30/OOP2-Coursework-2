import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CardTest {
    public static void main(String[] args) {
        Card card1 = new Card(1L, "Dragon", Rank.UNIQUE);
        Card card2 = new Card(2L, "Phoenix", Rank.RARE);
        Card card3 = new Card(3L, "Goblin", Rank.UNCOMMON);
        Card card4 = new Card(1L, "Dragon", Rank.UNIQUE); // Same as card1
        Card card5 = new Card(4L, "Elf", Rank.COMMON);

        // Test toString
        System.out.println(card1);
        System.out.println(card2);

        // Test equals
        assert card1.equals(card4);
        assert !card1.equals(card2);

        // Test hashCode consistency
        assert card1.hashCode() == card4.hashCode();

        // Test compareTo
        assert card1.compareTo(card2) < 0; // UNIQUE < RARE
        assert card2.compareTo(card3) < 0; // RARE < UNCOMMON
        assert card3.compareTo(card5) < 0; // UNCOMMON < COMMON
        assert card1.compareTo(card4) == 0; // identical cards

        // Test HashSet behavior
        Set<Card> hashSet = new HashSet<>();
        hashSet.add(card1);
        hashSet.add(card4); // Duplicate
        hashSet.add(card2);
        hashSet.add(card3);
        assert hashSet.size() == 3; // card1 and card4 are the same

        // Test TreeSet behavior (sorted set)
        Set<Card> treeSet = new TreeSet<>();
        treeSet.add(card2);
        treeSet.add(card5);
        treeSet.add(card3);
        treeSet.add(card1);
        treeSet.add(card4); // Duplicate
        assert treeSet.size() == 4; // card1 and card4 same

        System.out.println("TreeSet order:");
        for (Card c : treeSet) {
            System.out.println(c);
        }

        System.out.println("All Card tests passed!");
    }
}