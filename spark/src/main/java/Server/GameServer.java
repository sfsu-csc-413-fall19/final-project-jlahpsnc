package Server;

import static spark.Spark.*;

import DAO.GameStateServerDao;
import DAO.PlayerMongoDao;
import DataObjects.GameState;
import DataObjects.Player;
import Response.ResponseTemplate;
import WebSocket.WebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.tools.javac.util.Pair;
import spark.Request;
import spark.Response;
import org.eclipse.jetty.websocket.api.Session;

import java.util.*;

public class GameServer {
   // List of current players waiting in queue
   static ArrayList<Pair<Player, Session>> queueList = new ArrayList<>();

   //List of current games going on
   static ArrayList<GameState> gameList = new ArrayList<>();
   static int totalGames = 0;

   public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
    port(1234);

    webSocket("/wsLoading", WebSocketHandler.class);

    post("/login", GameServer::logIn);

    post("/register", GameServer::register);

    post("/logout", GameServer::logoutViaHTTP);

    get("/playerInfo", GameServer::playerInfo);

    get("/rankings", GameServer::rankings);
    }

    private static String logIn(Request request, Response response) {
        String username = request.queryMap("username").value();
        String password = request.queryMap("password").value();
        if (request.queryParams().size() == 2 && username != null && password != null) {
            Player receivedPlayer = PlayerMongoDao.getInstance().getPlayerByUsername(username);
            if (receivedPlayer != null) {
                if (receivedPlayer.password.equals(password)) {
                    if (!receivedPlayer.isLoggedIn) {
                        PlayerMongoDao.getInstance().updatePlayerLoggedStatusById(receivedPlayer._id, true);
                        ResponseTemplate messageToReturn = new ResponseTemplate("Login Success", receivedPlayer._id);
                        return gson.toJson(messageToReturn);
                    } else {
                        ResponseTemplate messageToReturn = new ResponseTemplate("Login Failed", "User is logged in");
                        return gson.toJson(messageToReturn);
                    }
                } else {
                    ResponseTemplate messageToReturn = new ResponseTemplate("Login Failed", "Invalid password");
                    return gson.toJson(messageToReturn);
                }
            } else {
                ResponseTemplate messageToReturn = new ResponseTemplate("Login Failed", "Invalid username");
                return gson.toJson(messageToReturn);
            }
        } else {
            ResponseTemplate messageToReturn = new ResponseTemplate("Login Failed", "Invalid query");
            return gson.toJson(messageToReturn);
        }
    }

    private static String register(Request request, Response response) {
        String username = request.queryMap("username").value();
        String password = request.queryMap("password").value();
        if (request.queryParams().size() == 2 && username != null && password != null) {
            String newUserId = PlayerMongoDao.getInstance().addPlayerToDatabase(username, password);
            if (newUserId != null) {
                ResponseTemplate messageToReturn = new ResponseTemplate("Register Success", newUserId);
                return gson.toJson(messageToReturn);
            } else {
                ResponseTemplate messageToReturn = new ResponseTemplate("Register Failed", "Invalid username");
                return gson.toJson(messageToReturn);
            }
        } else {
            ResponseTemplate messageToReturn = new ResponseTemplate("Register Failed", "Invalid query");
            return gson.toJson(messageToReturn);
        }
    }

    private static String playerInfo(Request request, Response response) {
        String playerId = request.queryMap("playerId").value();
        if (request.queryParams().size() == 1 && playerId != null) {
            Player playerToReturn = PlayerMongoDao.getInstance().getPlayerById(playerId);
            if (playerToReturn != null) {
                ResponseTemplate messageToReturn = new ResponseTemplate("Player Info Success", gson.toJson(playerToReturn));
                return gson.toJson(messageToReturn);
            } else {
                ResponseTemplate messageToReturn = new ResponseTemplate("Player Info Failed", "Invalid ID");
                return gson.toJson(messageToReturn);
            }
        } else {
            ResponseTemplate messageToReturn = new ResponseTemplate("Player Info Failed", "Invalid query");
            return gson.toJson(messageToReturn);
        }
    }

    private static String logoutViaHTTP(Request request, Response response) {
        String playerId = request.queryMap("playerId").value();
        Player player = PlayerMongoDao.getInstance().getPlayerById(playerId);
        if (player != null) {
            if (player.isLoggedIn) {
                PlayerMongoDao.getInstance().updatePlayerGameStatusById(player._id, false, false);
                PlayerMongoDao.getInstance().updatePlayerLoggedStatusById(player._id, false);
                ResponseTemplate messageToReturn = new ResponseTemplate("Logout Success", player._id);
                return gson.toJson(messageToReturn);
            } else {
                ResponseTemplate messageToReturn = new ResponseTemplate("Logout Failed", "User is not logged in");
                return gson.toJson(messageToReturn);
            }
        } else {
            ResponseTemplate messageToReturn = new ResponseTemplate("Logout Failed", "Invalid ID");
            return gson.toJson(messageToReturn);
        }
    }

    private static String rankings(Request request, Response response) {
        ArrayList<Player> allPlayers = PlayerMongoDao.getInstance().getAllPlayers();
        Collections.sort(allPlayers);

        int size = 10;
        if (allPlayers.size() < size) {
            size = allPlayers.size();
        }

        Player[] topPlayers = allPlayers.subList(0, size).toArray(new Player[size]);ResponseTemplate messageToReturn = new ResponseTemplate("Rankings Success", gson.toJson(topPlayers));
        return gson.toJson(messageToReturn);
    }

    // Helper function to create a game, given two players and their sessions
    private static void createGame(Pair<Player, Session> playerOne, Pair<Player, Session> playerTwo) {
        GameState newGame = new GameState(generateNewGameId(), playerOne.fst, playerOne.snd,
                playerTwo.fst, playerTwo.snd);

        gameList.add(newGame);
        PlayerMongoDao.getInstance().updatePlayerGameStatusById(playerOne.fst._id, false, true);
        PlayerMongoDao.getInstance().updatePlayerGameStatusById(playerTwo.fst._id, false, true);
        WebSocketHandler.newGameBroadcast(newGame);
    }

    // Returns an ongoing game from the game list, based on the gameId
    public static GameState getGameById(int gameId) {
      for (GameState game: gameList) {
          if (game.gameId == gameId) {
              return game;
          }
      }
      return null;
    }

    // Helper function to generate a new gameId, and update the count for the next game gameId
    private static int generateNewGameId() {
      totalGames++;
      return (totalGames);
    }

    public static void processMessage(String message, Session session) {
      ResponseTemplate response = gson.fromJson(message, ResponseTemplate.class);

      String responseType = response.responseType;
      switch (responseType) {
          case "Play Game":
              addPlayerToQueue(response.responseBody, session);
              break;

          case "Flip Card":
              flipCard(response.responseBody);
              break;

          case "Get Game":
              getGameState(response.responseBody, session);
              break;

          case "Logout":
              logoutViaWebsocket(response.responseBody, session);
              break;

          case "Disconnected":
              userDisconnected(response.responseBody, session);
              break;
      }
    }

    /*
    Get the game via websocket
    Format of response body must be as follows: "gameId"
    ex: "12"
     */
    public static void getGameState(String gameId, Session session) {
        GameState game = getGameById(Integer.parseInt(gameId));
        if (game != null) {
            WebSocketHandler.updateGame(game, session);
        }
    }

    /*
    Adds a player to queue, given their ID and session
    Format of response body must be as follows: "playerId"
    ex: "9F1d5q7"
    */
    public static void addPlayerToQueue(String playerId, Session session) {
        Player player = PlayerMongoDao.getInstance().getPlayerById(playerId);
        if (player != null && player.isLoggedIn && !player.inGame && !player.inQueue) {
            queueList.add(new Pair<>(player, session));
            PlayerMongoDao.getInstance().updatePlayerGameStatusById(player._id, true, false);

            // After adding a player to the queue, attempt to match them with someone else in the queue
            if (queueList.size() == 2) {
                Pair<Player, Session> playerOne = queueList.remove(0);
                PlayerMongoDao.getInstance().updatePlayerGameStatusById(playerOne.fst._id, false, false);
                Pair<Player, Session> playerTwo = queueList.remove(0);
                PlayerMongoDao.getInstance().updatePlayerGameStatusById(playerTwo.fst._id, false, false);

                createGame(playerOne, playerTwo);
            }
        }
    }

    /*
    Format of response body must be as follows: "gameId,playerId,x,y"
    ex: "5,9F1d5q7,3,7"
    */
    private static void flipCard(String flipInformation) {
        String[] splitString = flipInformation.split(",");
        if (splitString.length == 4) {
            int gameId = Integer.parseInt(splitString[0]);
            String playerId = splitString[1];
            int cardX = Integer.parseInt(splitString[2]);
            int cardY = Integer.parseInt(splitString[3]);

            GameStateServerDao.getInstance().flipCard(gameId, playerId, cardX, cardY);
        }
    }

    /*
    Format of response body must be as follows: "playerId"
    ex: "9F1d5q7"
    */
    private static void logoutViaWebsocket(String playerId, Session session) {
        Player player = PlayerMongoDao.getInstance().getPlayerById(playerId);
        ResponseTemplate messageToReturn;
        if (player != null) {
            if (player.isLoggedIn) {
                PlayerMongoDao.getInstance().updatePlayerGameStatusById(player._id, false, false);
                PlayerMongoDao.getInstance().updatePlayerLoggedStatusById(player._id, false);
                messageToReturn = new ResponseTemplate("Logout Success", player._id);
            } else {
                messageToReturn = new ResponseTemplate("Logout Failed", "User is not logged in");
            }
            WebSocketHandler.logoutBroadcast(messageToReturn, session);
        } else {
            messageToReturn = new ResponseTemplate("Logout Failed", "Invalid ID");
            WebSocketHandler.logoutBroadcast(messageToReturn, session);
        }
    }

    public static void removeGame(GameState game){
        if (game.gameIsOver) {
            gameList.remove(game);
        }
    }

    public static void userDisconnected(String playerId, Session session) {
        Player player = PlayerMongoDao.getInstance().getPlayerById(playerId);
        if (player != null) {
            if (player.isLoggedIn) {
                if (player.inQueue && queueList.size() > 0){
                    PlayerMongoDao.getInstance().updatePlayerGameStatusById(player._id, false, false);
                    queueList.remove(0);
                }
                else if (player.inGame){
                    PlayerMongoDao.getInstance().updatePlayerGameStatusById(player._id, false, false);
                    for (GameState game : gameList){
                        if (game.playerOne._id.equals( playerId)){
                            game.playerOne = PlayerMongoDao.getInstance().getPlayerById(playerId);
                            GameStateServerDao.getInstance().endGame(game.gameId);
                            break;
                        }
                        else if (game.playerTwo._id.equals( playerId)){
                            game.playerTwo = PlayerMongoDao.getInstance().getPlayerById(playerId);
                            GameStateServerDao.getInstance().endGame(game.gameId);
                            break;
                        }
                    }
                }
            }
        }
    }
}
