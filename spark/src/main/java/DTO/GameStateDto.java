package DTO;

import DAO.BoardDao;
import DTO.BoardDto;
import DTO.PlayerDto;

import java.util.Date;

public class GameStateDto {
    // Passive game info
    public final int gameId;
    public final Date startTime;

    // Players info
    public final PlayerDto playerOne;
    public final PlayerDto playerTwo;

    // Dynamic game info
    public final BoardDto gameBoard;
    public final int currentPlayersTurn;
    public final int playerOneScore;
    public final int playerTwoScore;
    public final int turnTimer;
    public final int numPairsLeft;
    public final CardDto cardFlipped;
    public final boolean jokerIsRevealed;

    public GameStateDto(int gameId, Date startTime, PlayerDto playerOne, PlayerDto playerTwo, BoardDto gameBoard, int currentPlayersTurn, int playerOneScore, int playerTwoScore, int turnTimer, int numPairsLeft, CardDto cardFlipped, boolean jokerIsRevealed) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameBoard = gameBoard;
        this.currentPlayersTurn = currentPlayersTurn;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        this.turnTimer = turnTimer;
        this.numPairsLeft = numPairsLeft;
        this.cardFlipped = cardFlipped;
        this.jokerIsRevealed = jokerIsRevealed;
    }
}
