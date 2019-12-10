package WebSocket;

import DataObjects.GameState;
import Server.MainServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;

@WebSocket
public class WebSocketHandler {
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @OnWebSocketConnect
    public void connected(Session session) throws IOException {
        System.out.println("A client has connected");
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) throws IOException{
        System.out.println("A client has disconnected");

        // Ask Niko
        // Reason parameter may not be userId, however, front end MUST send userId somehow
        // This may be changed, but userDisconnected() MUST be sent userId
        String userId = reason;
        MainServer.userDisconnected(userId, session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        MainServer.processMessage(message, session);
    }

    public static void updateGame(GameState game) {
        Response response = new Response();
        response.setResponseType("Update Game");
        response.setResponseBody(gson.toJson(game));

        try {
            game.playerOneSession.getRemote().sendString(gson.toJson(response));
            game.playerTwoSession.getRemote().sendString(gson.toJson(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updatePausedGame(GameState game) {
        Response response = new Response();
        response.setResponseType("Paused Game");
        response.setResponseBody(gson.toJson(game));

        try {
            game.playerOneSession.getRemote().sendString(gson.toJson(response));
            game.playerTwoSession.getRemote().sendString(gson.toJson(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newGameBroadcast(GameState game) {
        Response response = new Response();
        response.setResponseType("New Game");
        response.setResponseBody(gson.toJson(game));

        try {
            game.playerOneSession.getRemote().sendString(gson.toJson(response));
            game.playerTwoSession.getRemote().sendString(gson.toJson(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gameOverBroadcast(GameState game) {
        Response response = new Response();
        response.setResponseType("Game Over");
        response.setResponseBody(gson.toJson(game));

        try {
            game.playerOneSession.getRemote().sendString(gson.toJson(response));
            game.playerTwoSession.getRemote().sendString(gson.toJson(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
