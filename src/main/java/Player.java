import java.util.ArrayList;

public class Player {
    int playerNo;
    ArrayList<Card> hand;
    int score;

    public Player(int playerNo) {
        this.playerNo = playerNo;
        hand = new ArrayList<>();
        score = 0;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
