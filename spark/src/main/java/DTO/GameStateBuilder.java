package DTO;

import java.util.Date;

public class GameStateBuilder {
    private  int gameId;
    private  Date startTime =  new Date();
    private  PlayerDto playerOne;
    private  PlayerDto playerTwo;
    private int playerOneScore;
    private int playerTwoScore;
    private  BoardDto gameBoard;
    private  int currentPlayersTurn = 1;
    private  int turnTimer = 30;
    private int  numPairsLeft;
    private CardDto cardFlipped;
    private boolean jokerIsRevealed;

    public GameStateDto build() {
        GameStateDto stateToReturn = new GameStateDto(gameId, startTime, playerOne, playerTwo, gameBoard,
                currentPlayersTurn, playerOneScore, playerTwoScore, turnTimer, numPairsLeft, cardFlipped, jokerIsRevealed);
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

    public GameStateBuilder setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
        return this;
    }

    public GameStateBuilder setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
        return this;
    }

    public GameStateBuilder setNumPairsLeft(int numPairsLeft) {
        this.numPairsLeft = numPairsLeft;
        return this;
    }

    public GameStateBuilder setCardFlipped(CardDto cardFlipped) {
        this.cardFlipped = cardFlipped;
        return this;
    }

    public GameStateBuilder setJokerIsRevealed(boolean jokerIsRevealed) {
        this.jokerIsRevealed = jokerIsRevealed;
        return this;
    }
}
