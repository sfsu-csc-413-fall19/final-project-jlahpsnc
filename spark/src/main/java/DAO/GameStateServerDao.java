package DAO;

import DataObjects.GameState;
import Server.GameServer;
import WebSocket.WebSocketHandler;

public class GameStateServerDao {
    private static GameStateServerDao gameStateDaoObject;

    private GameStateServerDao() {};

    public static GameStateServerDao getInstance() {
        if (gameStateDaoObject == null) {
            gameStateDaoObject = new GameStateServerDao();
        }
        return gameStateDaoObject;
    }

    public boolean flipCard(int gameId, String playerAttemptingToFlipCard, int x, int y) {
        GameState game = GameServer.getGameById(gameId);
        if (game != null && game.currentPlayersTurn.equals(playerAttemptingToFlipCard) && game.gameBoard.getCard(x,y) != null) {
            // If the card is a joker
            if (game.gameBoard.getCard(x,y).cardId == 99) {
                increasePlayerScoreByOne(gameId, game.currentPlayersTurn);
                game.gameBoard.getCard(x,y).isRevealed = true;
                game.jokerIsRevealed = true;
                game.gameIsPaused = true;
                WebSocketHandler.updatePausedGame(game);
                game.gameIsPaused = false;
                game.gameBoard.getCard(x,y).isOffBoard = true;
            }
            // If no other card has been flipped before, simply flip this card over
            else if (game.cardFlipped == null) {
                game.gameBoard.getCard(x, y).isRevealed = true;
                game.cardFlipped = game.gameBoard.getCard(x, y);
                WebSocketHandler.updateGame(game);
            }
            // If a card has been flipped before, then we need to check if this new card bring flipped matches it
            else {
                // If the second card being flipped matches the already flipped card
                if (game.gameBoard.getCard(x,y).cardId == game.cardFlipped.cardId) {
                    game.gameBoard.getCard(x, y).isRevealed = true;
                    increasePlayerScoreByOne(gameId, game.currentPlayersTurn);
                    game.numPairsLeft--;
                    if (checkForGameOver(gameId)) {
                        game.gameIsOver = true;
                        WebSocketHandler.gameOverBroadcast(game);
                    } else {
                        game.gameIsPaused = true;
                        WebSocketHandler.updatePausedGame(game);
                        game.gameIsPaused = false;
                        game.gameBoard.getCard(game.cardFlipped.x, game.cardFlipped.y).isOffBoard = true;
                        game.gameBoard.getCard(x, y).isOffBoard = true;
                        game.cardFlipped = null;
                    }
                }
                // If the second card being flipped does not match the already flipped card
                else {
                    game.gameBoard.getCard(x,y).isRevealed = true;
                    changeTurns(gameId);
                    game.gameIsPaused = true;
                    WebSocketHandler.updatePausedGame(game);
                    game.gameBoard.getCard(x,y).isRevealed = false;
                    game.gameIsPaused = false;
                    game.cardFlipped = null;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean checkForGameOver(int gameId) {
        GameState game = GameServer.getGameById(gameId);
        if (game != null && game.numPairsLeft <= 0) {
            return true;
        }
        return false;
    }

    private boolean changeTurns(int gameId) {
        GameState game = GameServer.getGameById(gameId);
        if (game != null) {
            if (game.currentPlayersTurn == game.playerOne._id) {
                game.currentPlayersTurn = game.playerTwo._id;
                return true;
            }
            else if (game.currentPlayersTurn == game.playerTwo._id) {
                game.currentPlayersTurn = game.playerOne._id;
                return true;
            }
        }
        return false;
    }

    private boolean increasePlayerScoreByOne(int gameId, String playerId) {
        GameState game = GameServer.getGameById(gameId);
        if (game != null) {
            if (game.playerOne._id == playerId) {
                game.playerOneScore = game.playerOneScore + 1;
                return true;
            }
            else if (game.playerTwo._id == playerId) {
                game.playerTwoScore = game.playerOneScore + 1;
                return true;
            }
        }
        return false;
    }
}
