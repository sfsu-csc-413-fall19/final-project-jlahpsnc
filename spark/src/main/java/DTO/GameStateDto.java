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
    public BoardDto gameBoard;
    public String currentPlayersTurn;  // Must be the ID of the player
    public int playerOneScore;
    public int playerTwoScore;
    public int turnTimer;
    public int numPairsLeft;
    public CardDto cardFlipped;
    public boolean jokerIsRevealed;
    public boolean gameIsOver;

    public GameStateDto(int gameId, Date startTime, PlayerDto playerOne, PlayerDto playerTwo) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
}
