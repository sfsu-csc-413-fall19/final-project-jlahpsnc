package WebSocket;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class LoadingScreen {
    static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
    static String userCount = "0";

    public static void broadcast(String message) {
        sessionMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnWebSocketConnect
    public void connected(Session session) throws IOException {
        System.out.println("A client has connected");
        sessionMap.put(session, session);
        broadcast(Integer.toString(sessionMap.size()));
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) throws IOException{
        System.out.println("A client has disconnected");
        sessionMap.remove(session);
        broadcast(Integer.toString(sessionMap.size()));
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("\nNumber of actual users: " + sessionMap.size());
        System.out.println("Got: " + message);   // Print message
        userCount = message; // save the count
        broadcast(message);
    }
}
