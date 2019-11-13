package DTO;

public class CardDto {
    public final int cardId;
    public final boolean isRevealed;

    public CardDto(int cardId) {
        this.cardId = cardId;
        isRevealed = false;
    }
}
