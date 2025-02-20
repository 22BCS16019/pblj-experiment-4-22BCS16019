Question1
import java.util.*;

// Card class to represent a playing card
class Card {
    private String symbol;
    private String value;

    public Card(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + symbol;
    }
}

// CardCollection class to manage the cards
class CardCollection {
    private Collection<Card> cards;

    public CardCollection() {
        this.cards = new ArrayList<>();
    }

    // Method to add a card to the collection
    public void addCard(String symbol, String value) {
        cards.add(new Card(symbol, value));
    }

    // Method to get all cards of a specific symbol
    public List<Card> getCardsBySymbol(String symbol) {
        List<Card> result = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSymbol().equalsIgnoreCase(symbol)) {
                result.add(card);
            }
        }
        return result;
    }
}

// Main class to test the functionality
public class CardCollector {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardCollection cardCollection = new CardCollection();

        // Adding some cards
        cardCollection.addCard("Hearts", "Ace");
        cardCollection.addCard("Hearts", "King");
        cardCollection.addCard("Spades", "Queen");
        cardCollection.addCard("Diamonds", "Jack");
        cardCollection.addCard("Hearts", "10");

        // User input to find cards of a particular symbol
        System.out.print("Enter the symbol to find cards (Hearts, Spades, Diamonds, Clubs): ");
        String symbol = scanner.nextLine();

        List<Card> foundCards = cardCollection.getCardsBySymbol(symbol);
        if (foundCards.isEmpty()) {
            System.out.println("No cards found for the symbol " + symbol);
        } else {
            System.out.println("Cards found: " + foundCards);
        }
        scanner.close();
    }
}

Question2
import java.util.*;

class TicketBookingSystem {
    private final boolean[] seats;

    public TicketBookingSystem(int totalSeats) {
        this.seats = new boolean[totalSeats];
    }

    public synchronized boolean bookSeat(int seatNumber, String customer) {
        if (seatNumber < 0 || seatNumber >= seats.length) {
            System.out.println(customer + " - Invalid seat number: " + seatNumber);
            return false;
        }
        if (!seats[seatNumber]) {
            seats[seatNumber] = true;
            System.out.println(customer + " successfully booked seat " + seatNumber);
            return true;
        } else {
            System.out.println(customer + " - Seat " + seatNumber + " is already booked.");
            return false;
        }
    }
}

class CustomerThread extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final int seatNumber;
    private final String customerName;

    public CustomerThread(TicketBookingSystem bookingSystem, int seatNumber, String customerName, int priority) {
        this.bookingSystem = bookingSystem;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, customerName);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(10);

        List<Thread> customers = new ArrayList<>();
        customers.add(new CustomerThread(bookingSystem, 2, "VIP_John", Thread.MAX_PRIORITY));
        customers.add(new CustomerThread(bookingSystem, 2, "Regular_Alice", Thread.NORM_PRIORITY));
        customers.add(new CustomerThread(bookingSystem, 5, "VIP_Mary", Thread.MAX_PRIORITY));
        customers.add(new CustomerThread(bookingSystem, 5, "Regular_Bob", Thread.NORM_PRIORITY));

        for (Thread customer : customers) {
            customer.start();
        }

        for (Thread customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
