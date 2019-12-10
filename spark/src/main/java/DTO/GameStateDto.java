package DTO;

import org.eclipse.jetty.websocket.api.Session;

import java.util.Date;

public class GameStateDto {
    // Passive game info
    public final int gameId;
    public final Date startTime;

    // Players info
    public final PlayerDto playerOne;
    public final PlayerDto playerTwo;
    public final Session playerOneSession;
    public final Session playerTwoSession;

    // Dynamic game info
    public BoardDto gameBoard;
    public String currentPlayersTurn;  // Must be the ID of the player
    public int playerOneScore = 0;
    public int playerTwoScore = 0;
    public int numPairsLeft = 12;
    public CardDto cardFlipped = null;
    public boolean jokerIsRevealed = false;
    public boolean gameIsPaused = false;
    public boolean gameIsOver = false;

    public GameStateDto(int gameId, PlayerDto playerOne, Session playerOneSession,
                        PlayerDto playerTwo, Session playerTwoSession) {
        this.gameId = gameId;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.startTime = new Date();
        this.playerOneSession = playerOneSession;
        this.playerTwoSession = playerTwoSession;
        this.gameBoard = BoardDto.generateNewBoard();
        this.currentPlayersTurn = playerOne._id;
    }
}
