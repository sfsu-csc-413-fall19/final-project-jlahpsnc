package DTO;

import java.util.Date;

public class GameStateBuilder {
    private  int gameId;
    private  Date startTime =  new Date();
    private  PlayerDto playerOne;
    private  PlayerDto playerTwo;
    private  BoardDto gameBoard;
    private  int currentPlayersTurn = 1;
    private  int turnTimer = 30;

    public GameStateDto build() {
        GameStateDto stateToReturn = new GameStateDto(gameId, startTime, playerOne, playerTwo,
                gameBoard, currentPlayersTurn, turnTimer);
        return stateToReturn;
    }

    public GameStateBuilder setGameId(int gameId) {
        this.gameId = gameId;
        return this;
    }

    public GameStateBuilder setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public GameStateBuilder setPlayerOne(PlayerDto playerOne) {
        this.playerOne = playerOne;
        return this;
    }

    public GameStateBuilder setPlayerTwo(PlayerDto playerTwo) {
        this.playerTwo = playerTwo;
        return this;
    }

    public GameStateBuilder setGameBoard(BoardDto gameBoard) {
        this.gameBoard = gameBoard;
        return this;
    }

    public GameStateBuilder setCurrentPlayersTurn(int currentPlayersTurn) {
        this.currentPlayersTurn = currentPlayersTurn;
        return this;
    }

    public GameStateBuilder setTurnTimer(int turnTimer) {
        this.turnTimer = turnTimer;
        return this;
    }
}
