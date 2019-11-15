import static spark.Spark.*;

import DAO.PlayerDao;
import DTO.GameStateDto;
import DTO.PlayerDto;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

public class MainServer {
   // List of current players waiting in queue
   static ArrayList<PlayerDto> queueList = new ArrayList<>();

   //List of current games going on
   static ArrayList<GameStateDto> gameList = new ArrayList<>();
  public static void main(String[] args) {
    port(1234);

    post("/login", MainServer::logIn);

    post("/home", MainServer::home);

    post("/play", MainServer::play);

    post("/quit", MainServer::quit);

    post("/rankings", MainServer::rankings);
  }

    private static String rankings(Request request, Response response) {
      return null;
    }

    private static String quit(Request request, Response response) {
      return null;
    }

    private static String home(Request request, Response response) {
      return null;
    }

    private static String logIn(Request request, Response response) {
      return null;
    }


    // User is put into a queue until a game is found
  private static String play(Request request, Response response) {
      return null;
  }

  // Goes through the queue list and matches players
  private static void findMatches() {
      while (queueList.size() >= 2) {
          PlayerDto playerOne = queueList.remove(0);
          PlayerDto playerTwo = queueList.remove(0);

          PlayerDao.getInstance().updatePlayerGameStatusById(playerOne._id, false, true);
          PlayerDao.getInstance().updatePlayerGameStatusById(playerTwo._id, false, true);
      }
  }
}
