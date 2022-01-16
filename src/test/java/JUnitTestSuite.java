import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JUnitTestSuite {
    @Test
    public void AIStaysIfScore21() {
        // creating new game with 4 players
        Game game = new Game();
        game.TOTAL_PLAYERS = 4;
        game.resetGame();

        // assigning AI AC and KH to score 21
        Player AI = game.players.get(game.AI_PLAYER);
        game.emptyPlayerHand(AI);
        game.assignCardToPlayer(AI, new Card("A", "C"));
        game.assignCardToPlayer(AI, new Card("K", "H"));
        game.updatePlayerScores();
        assertEquals(21, AI.getScore());
        System.out.println(game);

        // player 1 and 2 stays
        game.stay();
        game.stay();

        game.nextRound();

        // ensuring AI stayed
        assertEquals(2, AI.getHand().size());
        System.out.println(game);
        System.out.println(game.getGameResults());
    }

    @Test
    public void AIHitsIfPlayerHasAce() {
        // creating new game with 4 players
        Game game = new Game();
        game.TOTAL_PLAYERS = 4;
        game.resetGame();

        // assigning player 1 AH 3S and the AI 4C, QD
        Player player1 = game.players.get(0);
        game.emptyPlayerHand(player1);
        game.assignCardToPlayer(player1, new Card("A", "H"));
        game.assignCardToPlayer(player1, new Card("3", "S"));
        Player AI = game.players.get(game.AI_PLAYER);
        game.emptyPlayerHand(AI);
        game.assignCardToPlayer(AI, new Card("4", "C"));
        game.assignCardToPlayer(AI, new Card("Q", "D"));
        game.updatePlayerScores();
        assertTrue(player1.getScore() > 10);
        System.out.println(game);

        // player 1 and 2 stays
        game.stay();
        game.stay();

        game.nextRound();

        // ensuring AI hits
        assertTrue(AI.getHand().size() > 2);
        System.out.println(game);
        System.out.println(game.getGameResults());
    }

    @Test
    public void AIHitsIfPlayerHasJack() {
        // creating new game with 4 players
        Game game = new Game();
        game.TOTAL_PLAYERS = 4;
        game.resetGame();

        // assigning player 1 JH 3S and the AI 4C, QD
        Player player1 = game.players.get(0);
        game.emptyPlayerHand(player1);
        game.assignCardToPlayer(player1, new Card("J", "H"));
        game.assignCardToPlayer(player1, new Card("3", "S"));
        Player AI = game.players.get(game.AI_PLAYER);
        game.emptyPlayerHand(AI);
        game.assignCardToPlayer(AI, new Card("4", "C"));
        game.assignCardToPlayer(AI, new Card("Q", "D"));
        game.updatePlayerScores();
        assertTrue(player1.getScore() > 10);
        System.out.println(game);

        // player 1 and 2 stays
        game.stay();
        game.stay();

        game.nextRound();

        // ensuring AI hits
        assertTrue(AI.getHand().size() > 2);
        System.out.println(game);
        System.out.println(game.getGameResults());
    }

    @Test
    public void dealerStaysIfScore18() {
        // creating new game with 4 players
        Game game = new Game();
        game.TOTAL_PLAYERS = 4;
        game.resetGame();

        // assigning Dealer 8C and KH to score 18
        Player dealer = game.players.get(game.DEALER);
        game.emptyPlayerHand(dealer);
        game.assignCardToPlayer(dealer, new Card("8", "C"));
        game.assignCardToPlayer(dealer, new Card("K", "H"));
        game.updatePlayerScores();
        assertEquals(18, dealer.getScore());
        System.out.println(game);

        // player 1 and 2 stays
        game.stay();
        game.stay();

        game.nextRound();

        // ensuring Dealer stayed
        assertEquals(2, dealer.getHand().size());
        System.out.println(game);
        System.out.println(game.getGameResults());
    }

    @Test
    public void dealerHitsIfHasAceAndScore17() {
        // creating new game with 4 players
        Game game = new Game();
        game.TOTAL_PLAYERS = 4;
        game.resetGame();

        // assigning Dealer 6C and AH to score 17
        Player dealer = game.players.get(game.DEALER);
        game.emptyPlayerHand(dealer);
        game.assignCardToPlayer(dealer, new Card("6", "C"));
        game.assignCardToPlayer(dealer, new Card("A", "H"));
        game.updatePlayerScores();
        assertEquals(17, dealer.getScore());
        System.out.println(game);

        // player 1 and 2 stays
        game.stay();
        game.stay();

        game.nextRound();

        // ensuring Dealer hits
        assertTrue(dealer.getHand().size() > 2);
        System.out.println(game);
        System.out.println(game.getGameResults());
    }

    @Test
    public void aceScores() {
        // creating new game with 4 players
        Game game = new Game();
        game.TOTAL_PLAYERS = 4;
        game.resetGame();

        // assigning cards to players
        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);
        Player AI = game.players.get(game.AI_PLAYER);
        game.emptyPlayerHand(player1);
        game.assignCardToPlayer(player1, new Card("A", "C"));
        game.assignCardToPlayer(player1, new Card("3", "S"));
        game.assignCardToPlayer(player1, new Card("8", "H"));
        game.updatePlayerScores();
        assertEquals(12, player1.getScore());

        game.emptyPlayerHand(player2);
        game.assignCardToPlayer(player2, new Card("A", "S"));
        game.assignCardToPlayer(player2, new Card("A", "D"));
        game.updatePlayerScores();
        assertEquals(12, player2.getScore());

        game.emptyPlayerHand(AI);
        game.assignCardToPlayer(AI, new Card("A", "H"));
        game.assignCardToPlayer(AI, new Card("Q", "D"));
        game.updatePlayerScores();
        assertEquals(21, AI.getScore());
        System.out.println(game);
    }
}
