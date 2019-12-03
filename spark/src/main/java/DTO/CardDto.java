package DTO;

public class CardDto {
    public final int cardId;
    public boolean isRevealed;
    public boolean isPaired;
    public int x;
    public int y;

    public CardDto(int cardId) {
        this.cardId = cardId;
        this.isRevealed = false;
        this.isPaired = false;
        this.x = -1;
        this.y = -1;
    }

    public CardDto(int cardId, boolean isRevealed, boolean isPaired, int x, int y) {
        this.cardId = cardId;
        this.isRevealed = isRevealed;
        this.isPaired = isPaired;
        this.x = x;
        this.y = y;
    }
}
