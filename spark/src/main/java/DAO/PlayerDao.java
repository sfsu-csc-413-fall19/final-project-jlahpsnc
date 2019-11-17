package DAO;

import DTO.PlayerDto;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class PlayerDao {
    private static PlayerDao mongoDaoObject;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> playerCollection;

    public static PlayerDao getInstance() {
        if (mongoDaoObject == null) {
            mongoDaoObject = new PlayerDao();
        }
        return mongoDaoObject;
    }

    private PlayerDao() {
        // Open connection
        mongoClient = new MongoClient("localhost", 27017);
        // Get reference to database
        db = mongoClient.getDatabase("FinalDatabase");
        // Get reference to collection
        playerCollection = db.getCollection("PlayersCollection");
    }

    public String addPlayerToDatabase(String username, String password) {
        if (!usernameInUse(username)) {
            Document playerToAdd = new Document("username", username)
                    .append("password", password)
                    .append("highScore", 0)
                    .append("inQueue", false)
                    .append("inGame", false);

            playerCollection.insertOne(playerToAdd);
            String _id = playerToAdd.get("_id").toString();
            return _id;
        } else {
            return null;
        }
    }

    public PlayerDto getPlayerById(String id) {
        try {
            ObjectId idToSearch = new ObjectId(id);
            Document foundPlayer = playerCollection.find(eq("_id", idToSearch)).first();
            if (foundPlayer != null) {
                PlayerDto playerDtoToReturn = new PlayerDto(
                        foundPlayer.get("_id").toString(),
                        foundPlayer.get("username").toString(),
                        foundPlayer.get("password").toString(),
                        (int)foundPlayer.get("highScore"),
                        (boolean)foundPlayer.get("isLoggedIn"),
                        (boolean)foundPlayer.get("inQueue"),
                        (boolean) foundPlayer.get("inGame"));
                return playerDtoToReturn;
            }
            return new PlayerDto();
        }
        catch(Exception e) {
            return new PlayerDto();
        }
    }

    public PlayerDto getPlayerByUsername(String username) {
        Document foundPlayer = playerCollection.find(eq("username", username)).first();
        if (foundPlayer != null) {
            PlayerDto playerDtoToReturn = new PlayerDto(
                    foundPlayer.get("_id").toString(),
                    foundPlayer.get("username").toString(),
                    foundPlayer.get("password").toString(),
                    (int)foundPlayer.get("highScore"),
                    (boolean)foundPlayer.get("isLoggedIn"),
                    (boolean)foundPlayer.get("inQueue"),
                    (boolean) foundPlayer.get("inGame"));
            return playerDtoToReturn;
        }
        return new PlayerDto();
    }

    public ArrayList<PlayerDto> getAllPlayers() {
        ArrayList<PlayerDto> playersList = new ArrayList<>();
        MongoCursor<Document> cursor = playerCollection.find().iterator();

        try {
            while (cursor.hasNext()) {
                Document tempDoc = cursor.next();
                PlayerDto tempPlayer = new PlayerDto(
                        tempDoc.get("_id").toString(),
                        tempDoc.get("username").toString(),
                        tempDoc.get("password").toString(),
                        (int)tempDoc.get("highScore"),
                        (boolean)tempDoc.get("isLoggedIn"),
                        (boolean)tempDoc.get("inQueue"),
                        (boolean) tempDoc.get("inGame"));

                playersList.add(tempPlayer);
            }
        } finally {
            cursor.close();
        }
        return playersList;
    }

    public PlayerDto updatePlayerGameStatusById(String id, boolean inQueue, boolean inGame) {
        try {
            ObjectId idToSearch = new ObjectId(id);
            Document foundPlayer = playerCollection.find(eq("_id", idToSearch)).first();
            if (foundPlayer != null) {
                playerCollection.updateOne(eq("_id", idToSearch),
                        new Document("$set", new Document("inQueue", inQueue)));
                playerCollection.updateOne(eq("_id", idToSearch),
                        new Document("$set", new Document("inGame", inGame)));

                PlayerDto playerDtoToReturn = new PlayerDto(
                        foundPlayer.get("_id").toString(),
                        foundPlayer.get("username").toString(),
                        foundPlayer.get("password").toString(),
                        (int)foundPlayer.get("highScore"),
                        (boolean)foundPlayer.get("isLoggedIn"),
                        (boolean)foundPlayer.get("inQueue"),
                        (boolean) foundPlayer.get("inGame"));
                return playerDtoToReturn;
            }
            return new PlayerDto();
        }
        catch(Exception e) {
            return new PlayerDto();
        }
    }

    public PlayerDto updatePlayerLoggedStatusById(String id, boolean isLoggedIn) {
        try {
            ObjectId idToSearch = new ObjectId(id);
            Document foundPlayer = playerCollection.find(eq("_id", idToSearch)).first();
            if (foundPlayer != null) {
                playerCollection.updateOne(eq("_id", idToSearch),
                        new Document("$set", new Document("isLoggedIn", isLoggedIn)));

                PlayerDto playerDtoToReturn = new PlayerDto(
                        foundPlayer.get("_id").toString(),
                        foundPlayer.get("username").toString(),
                        foundPlayer.get("password").toString(),
                        (int)foundPlayer.get("highScore"),
                        (boolean)foundPlayer.get("isLoggedIn"),
                        (boolean)foundPlayer.get("inQueue"),
                        (boolean) foundPlayer.get("inGame"));
                return playerDtoToReturn;
            }
            return new PlayerDto();
        }
        catch(Exception e) {
            return new PlayerDto();
        }
    }

    public PlayerDto updatePlayerGameStatusByUsername(String username, boolean inQueue, boolean inGame) {
        Document foundPlayer = playerCollection.find(eq("username", username)).first();
        if (foundPlayer != null) {
            playerCollection.updateOne(eq("username", username),
                    new Document("$set", new Document("inQueue", inQueue)));
            playerCollection.updateOne(eq("username", username),
                    new Document("$set", new Document("inGame", inGame)));

            PlayerDto playerDtoToReturn = new PlayerDto(
                    foundPlayer.get("_id").toString(),
                    foundPlayer.get("username").toString(),
                    foundPlayer.get("password").toString(),
                    (int)foundPlayer.get("highScore"),
                    (boolean)foundPlayer.get("isLoggedIn"),
                    (boolean)foundPlayer.get("inQueue"),
                    (boolean) foundPlayer.get("inGame"));
            return playerDtoToReturn;
        }
        return new PlayerDto();
    }

    public boolean passwordIsCorrect(String username, String password) {
        Document foundPlayer = playerCollection.find(eq("username", username)).first();
        if (foundPlayer != null) {
            return (foundPlayer.get("password").toString().equals(password));
        }
        return false;
    }

    private boolean usernameInUse(String username) {
        Document foundPlayer = playerCollection.find(eq("username", username)).first();
        if (foundPlayer != null) {
            return true;
        }
        return false;
    }

}
