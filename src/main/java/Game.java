import java.util.ArrayList;
import java.util.Collections;

public class Game {
    final int TOTAL_PLAYERS = 3;
    final int DEALER = TOTAL_PLAYERS - 1;
    final int AI_PLAYER = DEALER - 1;
    ArrayList<Player> players;
    ArrayList<Card> deck;

    public Game() {
        resetGame();
    }

    public void resetGame() {
        players = new ArrayList<>();
        deck = new ArrayList<>();
        createDeck();
        shuffleDeck();
        createPlayers();
        firstDraw();
        updatePlayerScores();
    }

    private void createDeck() {
        String[] suits = {"C", "D", "H", "S"};
        for(String suit: suits) {
            deck.add(new Card("A", suit));
            for(int i = 2; i <= 10; i++) {
                deck.add(new Card(i+"", suit));
            }
            deck.add(new Card("J", suit));
            deck.add(new Card("Q", suit));
            deck.add(new Card("K", suit));
        }
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void printDeck() {
        for(Card card: deck) {
            System.out.println(card.toString());
        }
        System.out.println("Deck Size: " + deck.size());
    }

    private void createPlayers() {
        for(int i = 0; i < TOTAL_PLAYERS; i++) {
            players.add(new Player(i));
        }
    }

    private void firstDraw() {
        for(Player player: players) {
            distributeCardToPlayer(player.getPlayerNo());
        }
    }

    private void distributeCardToPlayer(int playerNo) {
        Player player = players.get(playerNo);
        Card card = deck.get(deck.size()-1);
        player.hand.add(card);
        deck.remove(card);
    }

    private void updatePlayerScores() {
        for(Player player: players) {
            int score = 0;
            for(Card card: player.hand) {
                score += getCardScore(card, true);
            }
            if(score > 21) {
                score = 0;
                for(Card card: player.hand) {
                    score += getCardScore(card, false);
                }
            }
            player.setScore(score);
        }
    }

    private int getCardScore(Card card, boolean maximizeAce) {
        int score;
        String rank = card.getRank();
        if(isNumber(rank)) {
            score = Integer.parseInt(rank);
        }
        else if(rank.equals("A")) {
            if(maximizeAce) score = 11;
            else score = 1;
        }
        else {
            score = 10;
        }
        return score;
    }

    private boolean isNumber(String numberString) {
        try{
            Integer.parseInt(numberString);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public String toString() {
        StringBuilder message = new StringBuilder();
        for(Player player: players) {
            int playerNo = player.getPlayerNo();
            if(playerNo == DEALER) {
                message.append("Dealer's hand:");
            }
            else if(playerNo == AI_PLAYER) {
                message.append("AI's hand:");
            }
            else {
                message.append("Player ").append(player.getPlayerNo() + 1).append("'s hand:");
            }
            for(Card card: player.getHand()) {
                message.append(" ").append(card.toString());
            }
            message.append("\nscore: ").append(player.getScore()).append("\n\n");
        }
        return message.toString();
    }
}
