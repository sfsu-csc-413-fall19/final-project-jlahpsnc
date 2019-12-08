package Server;

import static spark.Spark.*;

import DAO.PlayerDao;
import DTO.GameStateDto;
import DTO.PlayerDto;
import WebSocket.WebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import spark.Request;
import spark.Response;
import org.eclipse.jetty.websocket.api.Session;

import java.util.*;

public class MainServer {
   // List of current players waiting in queue
   static ArrayList<Pair<PlayerDto, Session>> queueList = new ArrayList<>();

   //List of current games going on
   static ArrayList<GameStateDto> gameList = new ArrayList<>();
   static int numberOfNextNewGame = 0;

  public static void main(String[] args) {
    port(1234);

    webSocket("/wsLoading", WebSocketHandler.class);

    post("/login", MainServer::logIn);

    post("/register", MainServer::register);

    get("/playerInfo", MainServer::playerInfo);

    get("/rankings", MainServer::rankings);

    post("/quit", MainServer::quit);
  }

    private static String logIn(Request request, Response response) {
        String username = request.queryMap("username").value();
        String password = request.queryMap("password").value();
        PlayerDto receivedPlayer = PlayerDao.getInstance().getPlayerByUsername(username);
        if (receivedPlayer != null) {
            if (receivedPlayer.password.equals(password)) {
                //dont need to check if current player is logged in already. Will redirect to home page if it is
                PlayerDao.getInstance().updatePlayerLoggedStatusById(receivedPlayer._id, true);
                return receivedPlayer._id;
            } else {
                return "Incorrect Password";
            }
        } else {
            return "Player not found";
        }
    }

    private static String register(Request request, Response response) {
        String username = request.queryMap("username").value();
        String password = request.queryMap("password").value();
        String newUserId = PlayerDao.getInstance().addPlayerToDatabase(username, password);

        if (newUserId != null) {
            PlayerDao.getInstance().updatePlayerLoggedStatusById(newUserId, true);
            return newUserId;
        } else {
            return "Player already exists";
        }
    }

    private static String playerInfo(Request request, Response response) {
        String playerId = request.queryMap("playerId").value();
        if (playerId != null) {
            PlayerDto playerInfoToReturn = PlayerDao.getInstance().getPlayerById(playerId);
            if (playerInfoToReturn._id != null) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                return gson.toJson(playerInfoToReturn);
            } else {
                return "Player with that id does not exist";
            }
        } else {
            return "No playerId passed in via request parameters";
        }
    }

    private static String rankings(Request request, Response response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int size = 0;
        ArrayList<PlayerDto> allPlayers = PlayerDao.getInstance().getAllPlayers();

        // Players being sorter by ranking based on function compareTo in PlayerDto class
        Collections.sort(allPlayers);

        if (allPlayers.size() < 10) {
            size = allPlayers.size();
        } else {
            size = 10;
        }
        PlayerDto[] topPlayers = allPlayers.subList(0, size).toArray(new PlayerDto[allPlayers.size()]);

        return gson.toJson(topPlayers);
    }

    private static String quit(Request request, Response response) {
        String playerId = request.queryMap("playerId").value();
        //dont need to check if its null. Player can only log out if they are already logged in.
        if (playerId != null) {
            PlayerDto player = PlayerDao.getInstance().getPlayerById(playerId);
            if (player._id != null) {
                if (player.isLoggedIn) {
                    PlayerDao.getInstance().updatePlayerLoggedStatusById(player._id, false);
                    return "Player was logged out";
                } else {
                    return "Player is not logged in";
                }
            } else {
                return "Player with that id does not exist";
            }
        } else {
            return "No playerId passed in via request parameters";
        }
    }

    // Takes a player and adds them to queue
    public static boolean addPlayerToQueue(String playerId, Session session) {
      PlayerDto player = PlayerDao.getInstance().getPlayerById(playerId);
        if (player != null) {
            PlayerDao.getInstance().updatePlayerGameStatusById(player._id, true, false);
            queueList.add(new Pair<>(player, session));

            // After adding a player to the queue, attempt to match them with someone else in the queue
            if (queueList.size() == 2) {
                Pair<PlayerDto, Session> playerOne = queueList.remove(0);
                PlayerDao.getInstance().updatePlayerGameStatusById(playerOne.getKey()._id, false, true);
                Pair<PlayerDto, Session> playerTwo = queueList.remove(0);
                PlayerDao.getInstance().updatePlayerGameStatusById(playerTwo.getKey()._id, false, true);

                createGame(playerOne, playerTwo);
            }
            return true;
        }
        else {
            return false;
        }
    }

    // Helper function to create a game given two players and their sessions
    private static void createGame(Pair<PlayerDto, Session> playerOne, Pair<PlayerDto, Session> playerTwo) {
        GameStateDto newGame = new GameStateDto(generateNewGameId(), playerOne.getKey(), playerOne.getValue(),
                playerTwo.getKey(), playerTwo.getValue());

        gameList.add(newGame);
        WebSocketHandler.newGameBroadcast(newGame);
    }

    // Returns an ongoing game from the game list, based on the gameId
    public static GameStateDto getGameById(int gameId) {
      for (GameStateDto game: gameList) {
          if (game.gameId == gameId) {
              return game;
          }
      }
      return null;
    }

    // Removes an ongoing game from the game list, based on the gameId
    public static boolean removeGameById(int gameId) {
        for (GameStateDto game: gameList) {
            if (game.gameId == gameId) {
                PlayerDao.getInstance().updatePlayerGameStatusById(game.playerOne._id, false, false);
                PlayerDao.getInstance().updatePlayerGameStatusById(game.playerTwo._id, false, false);
                gameList.remove(game);
                return true;
            }
        }
        return false;
    }

    // Helper function to generate a new gameId, and update the count for the next game gameId
    private static int generateNewGameId() {
      numberOfNextNewGame++;
      return (numberOfNextNewGame - 1);
    }
}
