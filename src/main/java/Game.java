import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Game implements Serializable {
    final int TOTAL_PLAYERS = 4;
    final int DEALER = TOTAL_PLAYERS - 1;
    final int AI_PLAYER = DEALER - 1;
    int players_connected = 0;
    int current_player;
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
        firstRound();
        updatePlayerScores();
        current_player = 0;
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

    private void firstRound() {
        for(Player player: players) {
            distributeCardToPlayer(player.getPlayerNo());
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

    public void hit() {
        System.out.println(getPlayerString(players.get(current_player)) + " decided to Hit!");
        distributeCardToPlayer(current_player);
        updatePlayerScores();
        if(isPlayerBusted()) {
            System.out.println("BUST!\n");
            updateCurrentPlayer();
        }
    }
    public void stay() {
        System.out.println(getPlayerString(players.get(current_player)) + " decided to Stay.");
        updateCurrentPlayer();
    }

    private boolean isPlayerBusted() {
        int score = players.get(current_player).getScore();
        if(score > 21) return true;
        else return false;
    }

    public void turnAI() {
        Player AI = players.get(AI_PLAYER);
        int score = AI.getScore();
        if(score == 21) stay();
        else if(humanHasAceOr10()) hit();
        else if(score >= 18 && score <= 20) {
            if(humanHas10Less(score)) hit();
            else stay();
        }
        else hit();
    }

    private boolean humanHasAceOr10() {
        boolean result = false;
        for(int i = 0; i < players.size() - 2; i++) {
            Player player = players.get(i);
            if(getCardScore(player.getHand().get(0), true) >= 10) result = true;
        }
        return  result;
    }

    private boolean humanHas10Less(int score) {
        boolean result = false;
        for(int i = 0; i < players.size() - 2; i++) {
            Player player = players.get(i);
            if(getCardScore(player.getHand().get(0), true) > (score - 10)) result = true;
        }
        return  result;
    }

    public void turnDealer() {
        Player dealer = players.get(DEALER);
        int score = dealer.getScore();
        if(score < 17) hit();
        else if(score == 17 && !hasAceInHand(dealer)) stay();
        else if(score == 17 && hasAceInHand(dealer)) hit();
        else stay();
    }

    private boolean hasAceInHand(Player player) {
        boolean result = false;
        for(Card card: player.hand) {
            if (card.getRank().equals("A")) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void nextRound() {
        while(current_player != 0) {
            if(current_player == AI_PLAYER) {
                turnAI();
            }
            else if(current_player == DEALER) {
                turnDealer();
            }
        }
    }

    public void updateCurrentPlayer() {
        if(current_player == DEALER) current_player = 0;
        else current_player++;
    }

    private boolean isNumber(String numberString) {
        try{
            Integer.parseInt(numberString);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private String getPlayerString(Player player) {
        String playerString;
        int playerNo = player.getPlayerNo();
        if(playerNo == DEALER) {
            playerString = "Dealer";
        }
        else if(playerNo == AI_PLAYER) {
            playerString = "AI";
        }
        else {
            playerString = "Player "+ (player.getPlayerNo() + 1);
        }
        return playerString;
    }

    public String toString() {
        StringBuilder message = new StringBuilder();
        for(Player player: players) {
            message.append(getPlayerString(player)).append("'s hand:");
            for(Card card: player.getHand()) {
                message.append(" ").append(card.toString());
            }
            message.append("\nscore: ").append(player.getScore()).append("\n\n");
        }
        message.append("Player ").append(current_player + 1).append("'s turn.");
        return message.toString();
    }

    public String toStringClient(int clientNo) {
        StringBuilder message = new StringBuilder();
        for(Player player: players) {
            int playerNo = player.getPlayerNo();
            if(clientNo == playerNo) {
                message.append("Your hand:");
                for(Card card: player.getHand()) {
                    message.append(" ").append(card.toString());
                }
                message.append("\nscore: ").append(player.getScore()).append("\n\n");
            }
            else {
                message.append(getPlayerString(player)).append("'s hand:").append(player.getHand().get(0).toString()).append("\n\n");
            }
        }
        if(clientNo == current_player) message.append("Your turn.");
        else message.append("Player ").append(current_player + 1).append("'s turn.");
        message.append(" Hit or Stay? (h/s)");
        return message.toString();
    }
}
