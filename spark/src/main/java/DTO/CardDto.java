package DTO;

public class CardDto {
    public final int cardId;
    public final boolean isRevealed;
    public final boolean isPaired;

    public CardDto(int cardId) {
        this.cardId = cardId;
        isRevealed = false;
        isPaired = false;
    }
}
