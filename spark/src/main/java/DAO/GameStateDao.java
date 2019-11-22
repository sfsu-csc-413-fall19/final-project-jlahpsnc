package DAO;

import DTO.CardDto;
import DTO.GameStateDto;

public class GameStateDao {
    private static GameStateDao gameStateDaoObject;

    private GameStateDao() {};

    public static GameStateDao getInstance() {
        if (gameStateDaoObject == null) {
            gameStateDaoObject = new GameStateDao();
        }
        return gameStateDaoObject;
    }

    public void flipCard(int gameId, int x, int y) {
    }

    private boolean isPair(CardDto cardOne, CardDto cardTwo) {
        return cardOne.cardId == cardTwo.cardId;
    }

    private boolean gameOver(int gameId) {
        return false;
    }

    private void changeTurns(int gameId) {

    }

    private void updatePlayerScore(int gameId, String playerId, int newScore) {

    }
}
