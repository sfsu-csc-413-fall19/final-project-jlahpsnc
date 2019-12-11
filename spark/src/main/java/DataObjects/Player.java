package DataObjects;

public class Player implements Comparable{
    public final String _id;
    public final String username;
    public final String password;
    public final int highScore;
    public final boolean isLoggedIn;
    public final boolean inQueue;
    public final boolean inGame;

    public Player(String _id, String username, String password, int highScore,
                  boolean isLoggedIn, boolean inQueue, boolean inGame) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.highScore = highScore;
        this.isLoggedIn = isLoggedIn;
        this.inQueue = inQueue;
        this.inGame = inGame;
    }

    @Override
    public int compareTo(Object player) {
        int compareScore=((Player)player).highScore;

        /* For Descending order */
        return compareScore-this.highScore;
    }
}
