package WebSocket;

import DataObjects.GameState;
import Response.ResponseTemplate;
import Server.GameServer;
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
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) throws IOException{
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        GameServer.processMessage(message, session);
    }

    public static void updateGame(GameState game, Session session) {
        ResponseTemplate response = new ResponseTemplate();
        response.setResponseType("Update Game");
        response.setResponseBody(gson.toJson(game.getGameStateDto()));
        try {
            if (session.isOpen()) {
                session.getRemote().sendString(gson.toJson(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateGameBroadcast(GameState game) {
        ResponseTemplate response = new ResponseTemplate();
        response.setResponseType("Update Game");
        response.setResponseBody(gson.toJson(game.getGameStateDto()));
        try {
            if (game.playerOneSession.isOpen()) {
                game.playerOneSession.getRemote().sendString(gson.toJson(response));
            }
            if (game.playerTwoSession.isOpen()) {
                game.playerTwoSession.getRemote().sendString(gson.toJson(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pausedGameBroadcast(GameState game) {
        ResponseTemplate response = new ResponseTemplate();
        response.setResponseType("Paused Game");
        response.setResponseBody(gson.toJson(game.getGameStateDto()));
        try {
            if (game.playerOneSession.isOpen()) {
                game.playerOneSession.getRemote().sendString(gson.toJson(response));
            }
            if (game.playerTwoSession.isOpen()) {
                game.playerTwoSession.getRemote().sendString(gson.toJson(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newGameBroadcast(GameState game) {
        ResponseTemplate response = new ResponseTemplate();
        response.setResponseType("New Game");
        response.setResponseBody(gson.toJson(game.getGameStateDto()));

        try {
            if (game.playerOneSession.isOpen()) {
                game.playerOneSession.getRemote().sendString(gson.toJson(response));
            }
            if (game.playerTwoSession.isOpen()) {
                game.playerTwoSession.getRemote().sendString(gson.toJson(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gameOverBroadcast(GameState game) {
        ResponseTemplate response = new ResponseTemplate();
        response.setResponseType("Game Over");
        response.setResponseBody(gson.toJson(game.getGameStateDto()));

        try {
            if (game.playerOneSession.isOpen()) {
                game.playerOneSession.getRemote().sendString(gson.toJson(response));
            }
            if (game.playerTwoSession.isOpen()) {
                game.playerTwoSession.getRemote().sendString(gson.toJson(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void logoutBroadcast(ResponseTemplate response, Session session) {
        try {
            if (session.isOpen()) {
                session.getRemote().sendString(gson.toJson(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
