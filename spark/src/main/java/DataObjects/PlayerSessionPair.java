package DataObjects;

import org.eclipse.jetty.websocket.api.Session;

public class PlayerSessionPair {
    public Player fst;
    public Session snd;

    public PlayerSessionPair(Player player, Session session) {
        fst = player;
        snd = session;
    }
}
