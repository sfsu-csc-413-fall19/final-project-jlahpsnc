package DTO;

public class CardDto {
    public final int cardId;
    public boolean isRevealed;
    public boolean isOffBoard;
    public int x;
    public int y;

    public CardDto(int cardId) {
        this.cardId = cardId;
        this.isRevealed = false;
        this.isOffBoard = false;
        this.x = 0;
        this.y = 0;
    }
}
