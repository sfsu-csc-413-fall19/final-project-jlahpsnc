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
    public static Integer userCountInt = 0;

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
        System.out.println(session.getRemote());
        sessionMap.put(session, null);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) throws IOException{
        System.out.println("A client has disconnected");
        sessionMap.remove(session);
        broadcast(""+userCountInt);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("\nNumber of actual users: " + sessionMap.size());
        userCountInt += Integer.parseInt(message);
        userCountInt = userCountInt > sessionMap.size() ? sessionMap.size() : userCountInt;
        System.out.println("Got: " + userCountInt);   // Print message
        userCount = userCountInt.toString(); // save the count
        broadcast(userCount);
    }
}
