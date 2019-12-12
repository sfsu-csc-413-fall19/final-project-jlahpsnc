package DAO;

import DataObjects.Card;
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
                if (game.cardFlipped == null) {
                    game.gameBoard.getCard(x, y).isRevealed = true;
                    game.jokerIsRevealed = true;
                    game.gameIsPaused = true;
                    String currentPlayersTurn = game.currentPlayersTurn;
                    game.currentPlayersTurn = null;
                    WebSocketHandler.pausedGameBroadcast(game);
                    game.currentPlayersTurn = currentPlayersTurn;
                    increasePlayerScoreByOne(gameId, game.currentPlayersTurn);
                    game.gameIsPaused = false;
                    game.gameBoard.getCard(x, y).isOffBoard = true;
                } else {
                    game.gameBoard.getCard(x, y).isRevealed = true;
                    game.jokerIsRevealed = true;
                    Card matchingcard = game.gameBoard.getMatchingCardById(game.cardFlipped.cardId, game);
                    matchingcard.isRevealed = true;
                    game.gameIsPaused = true;
                    String currentPlayersTurn = game.currentPlayersTurn;
                    game.currentPlayersTurn = null;
                    WebSocketHandler.pausedGameBroadcast(game);
                    game.currentPlayersTurn = currentPlayersTurn;
                    increasePlayerScoreByOne(gameId, game.currentPlayersTurn);
                    increasePlayerScoreByOne(gameId, game.currentPlayersTurn);
                    game.numPairsLeft--;
                    game.gameIsPaused = false;
                    game.gameBoard.getCard(x, y).isOffBoard = true;
                    game.gameBoard.getCard(game.cardFlipped.x, game.cardFlipped.y).isOffBoard = true;
                    matchingcard.isOffBoard = true;
                    game.cardFlipped = null;
                }
            }
            // If no other card has been flipped before, simply flip this card over
            else if (game.cardFlipped == null) {
                game.gameBoard.getCard(x, y).isRevealed = true;
                game.cardFlipped = game.gameBoard.getCard(x, y);
                WebSocketHandler.updateGameBroadcast(game);
            }
            // If a card has been flipped before, then we need to check if this new card bring flipped matches it
            else {
                // If the second card being flipped matches the already flipped card
                if (game.gameBoard.getCard(x,y).cardId == game.cardFlipped.cardId) {
                    game.gameBoard.getCard(x, y).isRevealed = true;
                    game.numPairsLeft--;
                    if (checkForGameOver(gameId)) {
                        endGame(gameId);
                    } else {
                        game.gameIsPaused = true;
                        String currentPlayersTurn = game.currentPlayersTurn;
                        game.currentPlayersTurn = null;
                        WebSocketHandler.pausedGameBroadcast(game);
                        game.currentPlayersTurn = currentPlayersTurn;
                        increasePlayerScoreByOne(gameId, game.currentPlayersTurn);
                        game.gameIsPaused = false;
                        game.gameBoard.getCard(game.cardFlipped.x, game.cardFlipped.y).isOffBoard = true;
                        game.gameBoard.getCard(x, y).isOffBoard = true;
                        game.cardFlipped = null;
                    }
                }
                // If the second card being flipped does not match the already flipped card
                else {
                    game.gameBoard.getCard(x,y).isRevealed = true;
                    game.gameIsPaused = true;
                    String currentPlayersTurn = game.currentPlayersTurn;
                    game.currentPlayersTurn = null;
                    WebSocketHandler.pausedGameBroadcast(game);
                    game.currentPlayersTurn = currentPlayersTurn;
                    changeTurns(gameId);
                    game.gameBoard.getCard(x,y).isRevealed = false;
                    game.gameBoard.getCard(game.cardFlipped.x, game.cardFlipped.y).isRevealed = false;
                    game.gameIsPaused = false;
                    game.cardFlipped = null;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void endGame(int gameId) {
        GameState game = GameServer.getGameById(gameId);
        if (game != null){
            int playerOneTotalScore = 0;
            int playerTwoTotalScore = 0;
            game.gameIsOver = true;
            // If the first player has left the game
            if (!game.playerOne.isLoggedIn && game.playerTwo.isLoggedIn) {
                playerTwoTotalScore = finalPlayerScore(game.playerTwoScore, true);
            }
            // If the second player has left the game
            else if (!game.playerTwo.isLoggedIn && game.playerOne.isLoggedIn) {
                playerOneTotalScore = finalPlayerScore(game.playerOneScore, true);
            }
            // If both players are still in game
            else {
                if (game.playerOneScore == game.playerTwoScore) {
                    playerOneTotalScore = finalPlayerScore(game.playerOneScore, null);
                    playerTwoTotalScore = finalPlayerScore(game.playerTwoScore, null);
                }
                else if (game.playerOneScore > game.playerTwoScore){
                    playerOneTotalScore = finalPlayerScore(game.playerOneScore, true);
                    playerTwoTotalScore = finalPlayerScore(game.playerTwoScore, false);
                }
                else {
                    playerOneTotalScore = finalPlayerScore(game.playerOneScore, false);
                    playerTwoTotalScore = finalPlayerScore(game.playerTwoScore, true);
                }
            }
            // Update scores in database
            PlayerMongoDao.getInstance().updatePlayerHighScoreById(game.playerOne._id, playerOneTotalScore);
            PlayerMongoDao.getInstance().updatePlayerHighScoreById(game.playerTwo._id, playerTwoTotalScore);
            // Update player status in database
            PlayerMongoDao.getInstance().updatePlayerGameStatusById(game.playerOne._id, false, false);
            PlayerMongoDao.getInstance().updatePlayerGameStatusById(game.playerTwo._id, false, false);
            // Update final score in game
            game.playerOneScore = playerOneTotalScore;
            game.playerTwoScore = playerTwoTotalScore;

            GameServer.removeGame(game);
            WebSocketHandler.gameOverBroadcast(game);
        }
    }

    private int finalPlayerScore (int numOfPairs, Boolean winner){
        int winningBonus = 500;
        int tryingBonus = 100;
        int pairMultiplier = 100;
        int tiedBonus = 200;

        if (winner == null) {
            return numOfPairs * pairMultiplier + tiedBonus;
        }
        else if (winner){
            return numOfPairs * pairMultiplier + winningBonus;
        }
        return numOfPairs * pairMultiplier + tryingBonus;
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
                game.playerTwoScore = game.playerTwoScore + 1;
                return true;
            }
        }
        return false;
    }
}
