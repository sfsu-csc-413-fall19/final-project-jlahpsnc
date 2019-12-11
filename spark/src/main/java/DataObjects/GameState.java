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

    public GameStateDto getGameStateDto() {
        GameStateDto gameToReturn = new GameStateDto(this.gameId, this.playerOne, this.playerTwo,
                this.gameBoard, this.currentPlayersTurn, this.playerOneScore, this.playerTwoScore,
                this.numPairsLeft, this.jokerIsRevealed, this.gameIsPaused, this.gameIsOver);

        return gameToReturn;
    }

    private class GameStateDto {
        // Passive game info
        public final int gameId;
        public final Player playerOne;
        public final Player playerTwo;
        public final GameBoard gameBoard;
        public final String currentPlayersTurn;  // Must be the ID of the player
        public final int playerOneScore;
        public final int playerTwoScore;
        public final int numPairsLeft;
        public final boolean jokerIsRevealed;
        public final boolean gameIsPaused;
        public final boolean gameIsOver;

        private GameStateDto(int gameId, Player playerOne, Player playerTwo,
                             GameBoard gameBoard, String currentPlayersTurn, int playerOneScore,
                             int playerTwoScore, int numPairsLeft, boolean jokerIsRevealed,
                             boolean gameIsPaused, boolean gameIsOver) {
            this.gameId = gameId;
            // Set password to null for both players
            this.playerOne = new Player(playerOne._id, playerOne.username, null, playerOne.highScore,
                    playerOne.isLoggedIn, playerOne.inQueue, playerOne.inQueue);
            this.playerTwo = new Player(playerTwo._id, playerTwo.username, null, playerTwo.highScore,
                    playerTwo.isLoggedIn, playerTwo.inQueue, playerTwo.inQueue);
            this.gameBoard = gameBoard;
            this.currentPlayersTurn = currentPlayersTurn;
            this.playerOneScore = playerOneScore;
            this.playerTwoScore = playerTwoScore;
            this.numPairsLeft = numPairsLeft;
            this.jokerIsRevealed = jokerIsRevealed;
            this.gameIsPaused = gameIsPaused;
            this.gameIsOver = gameIsOver;
        }
    }
}
