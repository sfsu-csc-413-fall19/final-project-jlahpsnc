import static spark.Spark.*;

import DAO.PlayerDao;
import DTO.GameStateDto;
import DTO.PlayerDto;
import WebSocket.LoadingScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainServer {
   // List of current players waiting in queue
   static ArrayList<PlayerDto> queueList = new ArrayList<>();

   //List of current games going on
   static ArrayList<GameStateDto> gameList = new ArrayList<>();

  public static void main(String[] args) {
    port(1234);

    webSocket("/wsLoading", LoadingScreen.class);

    post("/login", MainServer::logIn);

    post("/home", MainServer::home);

    post("/play", MainServer::play);

    post("/quit", MainServer::quit);

    post("/rankings", MainServer::rankings);

    post("/playerInfo", MainServer::playerInfo);
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
        ArrayList<PlayerDto> allPlayers = PlayerDao.getInstance().getAllPlayers();
        HashMap<String, Integer> playersRankings = new HashMap<String, Integer>();

        // Players being sorter by ranking based on function compareTo in PlayerDto class
        Collections.sort(allPlayers);

        for (int  i = 0; i < 10; i++){
            playersRankings.put(allPlayers.get(i).username, allPlayers.get(i).highScore);
        }
        return playersRankings.toString();
    }

    private static String quit(Request request, Response response) {
        String playerId = request.queryMap("playerId").value();
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

    private static String home(Request request, Response response) {
      return null;
    }

    private static String logIn(Request request, Response response) {
      String username = request.queryMap("username").value();
      String password = request.queryMap("password").value();
      PlayerDto receivedPlayer = PlayerDao.getInstance().getPlayerByUsername(username);

      if (receivedPlayer != null) {
            if (receivedPlayer.password == password) {
                if (!receivedPlayer.isLoggedIn){
                    PlayerDao.getInstance().updatePlayerLoggedStatusById(receivedPlayer._id, true);
                    return receivedPlayer._id;
                } else {
                    return "Player is already logged in";
                }
            } else {
                return "Incorrect Password";
            }
      } else {
          return "Player not found";
      }
    }

    // User is put into a queue until a game is found
    private static String play(Request request, Response response) {
        String playerId = request.queryMap("playerId").value();
        if (playerId != null) {
            PlayerDto player = PlayerDao.getInstance().getPlayerById(playerId);
            if (player._id != null) {
                addPlayerToQueue(player);
                return "Added player to queue";
            } else {
                return "Player with that id does not exist";
            }
        } else {
            return "No playerId passed in via request parameters";
        }
    }

    // Goes through the queue list and matches players to a game lobby
    private static void findMatches() {
        while (queueList.size() >= 2) {
          PlayerDto playerOne = queueList.remove(0);
          PlayerDto playerTwo = queueList.remove(0);

          PlayerDao.getInstance().updatePlayerGameStatusById(playerOne._id, false, true);
          PlayerDao.getInstance().updatePlayerGameStatusById(playerTwo._id, false, true);
        }
    }

    private static void addPlayerToQueue(PlayerDto player) {
      PlayerDao.getInstance().updatePlayerGameStatusById(player._id, true, false);
      queueList.add(player);
    }
}
