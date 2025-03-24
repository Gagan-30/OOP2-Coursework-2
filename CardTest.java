import java.util.HashSet;
import java.util.TreeSet;

public class CardTest {
    public static void main(String[] args) {
        // Test card creation and properties
        Card card1 = new Card(1, "Pikachu", Rank.COMMON);
        Card card2 = new Card(2, "Charizard", Rank.RARE);
        Card card3 = new Card(3, "Mewtwo", Rank.UNIQUE);
        Card card4 = new Card(4, "Bulbasaur", Rank.COMMON);
        Card card5 = new Card(5, "Squirtle", Rank.UNCOMMON);
        Card card6 = new Card(1, "Pikachu", Rank.COMMON); // Same as card1
        
        // Test toString
        System.out.println("Card 1: " + card1);
        System.out.println("Card 2: " + card2);
        System.out.println("Card 3: " + card3);
        
        // Test equals and hashCode
        assert card1.equals(card6) : "Cards with same properties should be equal";
        assert !card1.equals(card2) : "Cards with different properties should not be equal";
        assert card1.hashCode() == card6.hashCode() : "Equal cards should have same hash code";
        
        // Test compareTo
        assert card3.compareTo(card2) < 0 : "UNIQUE should come before RARE";
        assert card2.compareTo(card5) < 0 : "RARE should come before UNCOMMON";
        assert card1.compareTo(card4) < 0 : "Pikachu should come before Bulbasaur alphabetically";
        assert card1.compareTo(card6) == 0 : "Same cards should compare as equal";
        
        // Test HashSet behavior
        HashSet<Card> cardSet = new HashSet<>();
        cardSet.add(card1);
        cardSet.add(card2);
        cardSet.add(card6); // Should not be added as duplicate
        assert cardSet.size() == 2 : "HashSet should not contain duplicates";
        
        // Test TreeSet ordering
        TreeSet<Card> sortedCards = new TreeSet<>();
        sortedCards.add(card1);
        sortedCards.add(card2);
        sortedCards.add(card3);
        sortedCards.add(card4);
        sortedCards.add(card5);
        
        System.out.println("\nCards in TreeSet (should be sorted by rank, then name):");
        for (Card card : sortedCards) {
            System.out.println(card);
        }
        
        // Verify first card is the unique one
        assert sortedCards.first().equals(card3) : "First card should be the UNIQUE one";
        
        System.out.println("\nAll tests passed!");
    }
}
