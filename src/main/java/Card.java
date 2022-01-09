import java.io.Serializable;

public class Card implements Serializable {
    String rank, suite;

    public Card(String rank, String suite) {
        this.rank = rank;
        this.suite = suite;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String toString() {
        return this.rank + this.suite;
    }
}
