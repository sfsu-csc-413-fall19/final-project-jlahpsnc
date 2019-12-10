package DataObjects;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Date;

public class GameState {
    // Passive game info
    public final int gameId;
    public final Date startTime;

    // Players info
    public final Player playerOne;
    public final Player playerTwo;
    public final Session playerOneSession;
    public final Session playerTwoSession;

    // Dynamic game info
    public GameBoard gameBoard;
    public String currentPlayersTurn;  // Must be the ID of the player
    public int playerOneScore = 0;
    public int playerTwoScore = 0;
    public int numPairsLeft = 12;
    public Card cardFlipped = null;
    public boolean jokerIsRevealed = false;
    public boolean gameIsPaused = false;
    public boolean gameIsOver = false;

    public GameState(int gameId, Player playerOne, Session playerOneSession,
                     Player playerTwo, Session playerTwoSession) {
        this.gameId = gameId;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.startTime = new Date();
        this.playerOneSession = playerOneSession;
        this.playerTwoSession = playerTwoSession;
        this.gameBoard = GameBoard.generateNewBoard();
        this.currentPlayersTurn = playerOne._id;
    }
}
