package DataObjects;

public class Card {
    public final int cardId;
    public boolean isRevealed;
    public boolean isOffBoard;
    public int x;
    public int y;

    public Card(int cardId) {
        this.cardId = cardId;
        this.isRevealed = false;
        this.isOffBoard = false;
        this.x = 0;
        this.y = 0;
    }
}
