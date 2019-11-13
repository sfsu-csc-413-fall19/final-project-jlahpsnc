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

    // User is put into a queue until a game is found
    post("/queue", MainServer::queue);
  }


  // User is put into a queue until a game is found
  private static String queue(Request request, Response response) {
    // Check to make sure that a player username and password was sent via the parameter list
    String username = request.queryMap("username").value();
    String password = request.queryMap("password").value();
    if (username != null && password != null) {
        // Check to see if that player is already in the database, if not, add a new player
        PlayerDto player = PlayerDao.getInstance().getPlayerByUsername(username);
        if (player.username != null) {
            // Check to make sure the password they provided matches the password in the database
            if (PlayerDao.getInstance().passwordIsCorrect(username, password)) {
                PlayerDao.getInstance().updatePlayerGameStatusByUsername(username, true, false);
                queueList.add(PlayerDao.getInstance().getPlayerByUsername(username));
                return "Player added to queue";

            } else return "Password is incorrect";
        } else {
            String idOfAddedPlayer = PlayerDao.getInstance().addPlayerToDatabase(username, password);

            PlayerDao.getInstance().updatePlayerGameStatusById(idOfAddedPlayer, true, false);
            queueList.add(PlayerDao.getInstance().getPlayerById(idOfAddedPlayer));
            return "Player added to queue";
        }
    } else return "Username or password not passed in via parameters list";
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













//    // calling get will make your app start listening for the GET path with the /hello endpoint
//    get("/hello", (req, res) -> "Hello World");
//
//    get("/api", (req, res) -> {
//      System.out.println(req.queryMap().get("key").value());
//      String value = req.queryMap().get("key").value();
//      return "Hello " + value;
//    });
//
//    post("/postApi", (req, res) -> {
//      System.out.println(req.body());
//      NoteDto note = new NoteDto("oiergioergoij", "This is some note in mongo");
//      NoteDto note2 = new NoteDto("oiergioergoij", "This is some more text in mongo");
//      List<NoteDto> noteList = new ArrayList<>();
//      noteList.add(note);
//      noteList.add(note2);
//      Gson gson = new Gson();
//      return gson.toJson(noteList);
//    });
