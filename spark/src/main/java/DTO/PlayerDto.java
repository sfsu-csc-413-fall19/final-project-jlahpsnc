package DTO;

public class PlayerDto {
    public final String _id;
    public final String username;
    public final String password;
    public final int highScore;
    public final boolean isLoggedIn;
    public final boolean inQueue;
    public final boolean inGame;

    public PlayerDto() {
        this._id = null;
        this.username = null;
        this.password = null;
        this.highScore = 0;
        this.isLoggedIn = false;
        this.inQueue = false;
        this.inGame = false;
    }

    public PlayerDto(String _id, String username, String password, int highScore, boolean isLoggedIn, boolean inQueue, boolean inGame) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.highScore = highScore;
        this.isLoggedIn = isLoggedIn;
        this.inQueue = inQueue;
        this.inGame = inGame;
    }
}
