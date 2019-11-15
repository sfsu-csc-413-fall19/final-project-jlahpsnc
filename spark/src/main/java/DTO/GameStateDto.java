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
    public final int turnTimer;

    public GameStateDto(int gameId, Date startTime, PlayerDto playerOne, PlayerDto playerTwo,
                        BoardDto gameBoard, int currentPlayersTurn, int turnTimer) {
        this.gameId = gameId;
        this.startTime = startTime;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameBoard = gameBoard;
        this.currentPlayersTurn = currentPlayersTurn;
        this.turnTimer = turnTimer;
    }
}
