package WebSocket;

import DTO.GameStateDto;
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
        /* TODO
        Make sure to log user out when they close the tab
         */
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        /* TODO
        Parse string and see what type of message is being received
        Then send the body of that message to the correct processor
         */
    }

    public void updateGame(GameStateDto game) {
        try {
            message(game.playerOneSession, gson.toJson(game));
            message(game.playerTwoSession, gson.toJson(game));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newGameBroadcast(GameStateDto game) {
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
}
